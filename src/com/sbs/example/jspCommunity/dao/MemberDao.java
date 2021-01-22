package com.sbs.example.jspCommunity.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class MemberDao {

	public List<Member> getForPrintMembers() {
		List<Member> members = new ArrayList<>();

		SecSql sql = new SecSql();
		sql.append("SELECT M.*");
		sql.append("FROM `member` AS M");
		sql.append("ORDER BY M.id DESC");

		List<Map<String, Object>> mapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> map : mapList) {
			members.add(new Member(map));
		}

		return members;
	}

	public Member getMemberLoginId(String loginId) {
		SecSql sql = new SecSql();
		
		sql.append("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?",loginId);
				
		Map<String, Object> memberMap = MysqlUtil.selectRow(sql);
		
		if(memberMap.isEmpty()) {
			return null;
		}
		Member member = new Member(memberMap);
		return member;
	}

	public void join(Map<String, Object> joinArgs) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO `member`");
		sql.append("SET regDate = NOW(),");
		sql.append("updateDate = NOW(),");
		sql.append("loginId = ?,",joinArgs.get("loginId"));
		sql.append("loginPw = ?,",joinArgs.get("loginPw"));
		sql.append("cellphoneNo = ?,",joinArgs.get("cellphoneNo"));		
		sql.append("`name` = ?,",joinArgs.get("name"));
		sql.append("`email` = ?,",joinArgs.get("email"));
		sql.append("`nickname` = ?",joinArgs.get("nickname"));

		MysqlUtil.insert(sql);
		
	}

	public Member getMemberById(int id) {
		SecSql sql = new SecSql();
		sql.append("SELECT M.*");
		sql.append("FROM `member` AS M");
		sql.append("WHERE id = ?", id);

		Map<String, Object> map = MysqlUtil.selectRow(sql);

		if ( map.isEmpty() ) {
			return null;
		}

		return new Member(map);
	}

}
