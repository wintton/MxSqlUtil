package com.mx.main;

import java.sql.Connection;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.mx.handle.HandleManager;
import com.mx.handle.HandleUtil;
import com.mx.util.BatchSqlUtil;

/**
 * V1.0.2 2019-11-14 梦辛工作室
 * 
 * @author 灵
 *
 */
public class startUpClass {

	public static void main(String[] args) {

		// 新建一个处理管理类
		HandleManager handleManager = new HandleManager();

		// 向里面添加不同的处理
		handleManager.add(new HandleUtil("测试处理") {

			@Override
			public JSONObject getResult(HashMap<String, String> paramMap, Connection sqlConn) throws JSONException {
				// 在这里写入您的处理逻辑，请不要关闭sqlConn，再所有数据执行完后 ，会自动关闭
				// paramMap 包含所有请求参数，格式是xls文件每页第一列为key 第一列以下所有为value
				// 比如第一列为 name age 第二列为 灵 18 第三列为 灵 19 那么这里就会处理两次数据，第一次 为name为灵 age为18 第二次
				// name为灵 age为19
				// 选择导出数据的话会在数据源所在路径产生一个 源文件名_rep.xls文件 ，会将返回的json对象放在导出文件中
				String name = paramMap.get("name");
				String age = paramMap.get("age");

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("name", name);
				jsonObject.put("age", age);

				return jsonObject;
			}
		});

		// 创建视图
		BatchSqlUtil batchSqlUtil = new BatchSqlUtil(handleManager);
		batchSqlUtil.setJdbcUrl("设置默认的JDBC路径"); // 可写可不写
		batchSqlUtil.setAccount("默认数据库账号"); // 可写可不写
		batchSqlUtil.setPassword("默认的数据库密码"); // 可写可不写
		batchSqlUtil.show();
	}
}
