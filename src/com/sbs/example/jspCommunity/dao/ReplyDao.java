package com.sbs.example.jspCommunity.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.dto.Reply;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class ReplyDao {

	public List<Reply> getReplys(int articleId) {
		List<Reply> replys = new ArrayList<>();
		String article = "article";
		SecSql sql = new SecSql();
		sql.append("SELECT *");	
		sql.append("FROM reply");
		sql.append("WHERE relTypeCode = ?",article);
		sql.append("AND relId = ?",articleId);
		
		List<Map<String, Object>> replyMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> replyMap : replyMapList) {
			replys.add(new Reply(replyMap));
		}
		return replys;
	}

	public void modify(int replyId, String body) {
		SecSql sql = new SecSql();
		String article = "article";
		sql.append("UPDATE `reply`");
		sql.append("SET updateDate = NOW(),");
		sql.append("`body` = ?",body);
		sql.append("WHERE id = ?",replyId);
		sql.append("AND relTypeCode = ?",article);
		
		
		MysqlUtil.update(sql);
		
	}
}
