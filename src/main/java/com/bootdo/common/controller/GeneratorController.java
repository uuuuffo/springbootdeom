package com.bootdo.common.controller;

import com.alibaba.fastjson.JSON;
import com.bootdo.common.config.BootdoConfig;
import com.bootdo.common.config.DruidDBConfig;
import com.bootdo.common.service.GeneratorService;
import com.bootdo.common.utils.GenUtils;
import com.bootdo.common.utils.MysqlDbUtils;
import com.bootdo.common.utils.R;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库生成代码
 */
@RequestMapping("/common/generator")
@Controller
public class GeneratorController {
	String prefix = "common/generator";
	@Autowired
	GeneratorService generatorService;
	@Autowired
	BootdoConfig bootdoConfig;
	@Autowired
	DruidDBConfig druidDBConfig;

	// 数据库查看(不生成代码)
	@GetMapping("/show")
	String generat() {
		return prefix + "/show";
	}

	// 生成代码
	@GetMapping()
	String generator() {
		return prefix + "/list";
	}

	@ResponseBody
	@GetMapping("/list")
	List<Map<String, Object>> list() {
		List<Map<String, Object>> list = generatorService.list();
		return list;
	};

	@RequestMapping("/code/{tableName}")
	public void code(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("tableName") String tableName) throws IOException {
		String[] tableNames = new String[] { tableName };
		byte[] data = generatorService.generatorCode(tableNames);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"bootdo.zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");

		IOUtils.write(data, response.getOutputStream());
	}

	@RequestMapping("/batchCode")
	public void batchCode(HttpServletRequest request, HttpServletResponse response, String tables) throws IOException {
		String[] tableNames = new String[] {};
		tableNames = JSON.parseArray(tables).toArray(tableNames);
		byte[] data = generatorService.generatorCode(tableNames);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"bootdo.zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");

		IOUtils.write(data, response.getOutputStream());
	}

	@GetMapping("/edit")
	public String edit(Model model) {
		Configuration conf = GenUtils.getConfig();
		Map<String, Object> property = new HashMap<>(16);
		property.put("author", conf.getProperty("author"));
		property.put("email", conf.getProperty("email"));
		property.put("package", conf.getProperty("package"));
		property.put("autoRemovePre", conf.getProperty("autoRemovePre"));
		property.put("tablePrefix", conf.getProperty("tablePrefix"));
		model.addAttribute("property", property);
		return prefix + "/edit";
	}

	@ResponseBody
	@PostMapping("/update")
	R update(@RequestParam Map<String, Object> map) {
		try {
			PropertiesConfiguration conf = new PropertiesConfiguration("generator.properties");
			conf.setProperty("author", map.get("author"));
			conf.setProperty("email", map.get("email"));
			conf.setProperty("package", map.get("package"));
			conf.setProperty("autoRemovePre", map.get("autoRemovePre"));
			conf.setProperty("tablePrefix", map.get("tablePrefix"));
			conf.save();
		} catch (ConfigurationException e) {
			return R.error("保存配置文件出错");
		}
		return R.ok();
	}

	@ResponseBody
	@GetMapping("/dbBackup")
	R dbBackup() throws Exception {
		boolean r = MysqlDbUtils.dbBackup(druidDBConfig.getDbHost(), druidDBConfig.getDbPort(),
				druidDBConfig.getDbUsername(), druidDBConfig.getDbPassword(), druidDBConfig.getDbName(),
				bootdoConfig.getBackupPath());
		if (r) {
			return R.ok();
		} else {
			return R.error();
		}
	}

	@ResponseBody
	@GetMapping("/dbRecover")
	R dbRecover() throws Exception {
		boolean r = MysqlDbUtils.dbRecover(druidDBConfig.getDbHost(), druidDBConfig.getDbPort(),
				druidDBConfig.getDbUsername(), druidDBConfig.getDbPassword(), druidDBConfig.getDbName(),
				bootdoConfig.getBackupPath());
		if (r) {
			return R.ok();
		} else {
			return R.error();
		}
	}

}
