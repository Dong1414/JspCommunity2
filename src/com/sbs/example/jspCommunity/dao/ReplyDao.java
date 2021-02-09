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
		
		sql.append("SELECT R.*");
		sql.append(", IFNULL(SUM(L.point), 0) AS extra__likePoint");
		sql.append(", IFNULL(SUM(IF(L.point > 0, L.point, 0)), 0) AS extra__likeOnlyPoint");
		sql.append(", IFNULL(SUM(IF(L.point < 0, L.point * -1, 0)), 0) extra__dislikeOnlyPoint");
		sql.append("FROM reply AS R");
		sql.append("LEFT JOIN `like` AS L");
		sql.append("ON L.relTypeCode = 'reply'");		
		sql.append("WHERE R.relTypeCode = ?",article);
		sql.append("AND R.relId = ?",articleId);
		sql.append("GROUP BY R.id");
		sql.append("ORDER BY R.id DESC");
		
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
