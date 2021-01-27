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
		Member member = new Member(map);
		if ( map.isEmpty() ) {
			return null;
		}

		return member;
	}
	
	public Member getMemberByNameAndEmail(String name, String email) {
		SecSql sql = new SecSql();
		sql.append("SELECT M.*");
		sql.append("FROM `member` AS M");
		sql.append("WHERE name = ?", name);
		sql.append("AND email = ?", email);
		sql.append("ORDER BY id DESC");
		sql.append("LIMIT 1");

		Map<String, Object> map = MysqlUtil.selectRow(sql);

		if ( map.isEmpty() ) {
			return null;
		}

		return new Member(map);
	}
	public int modify(Map<String, Object> args) {
		SecSql sql = new SecSql();
		sql.append("UPDATE member");
		sql.append("SET updateDate = NOW()");

		boolean needToUpdate = false;

		if (args.get("loginPw") != null) {
			needToUpdate = true;
			sql.append(", loginPw = ?", args.get("loginPw"));
		}

		if (args.get("name") != null) {
			needToUpdate = true;
			sql.append(", `name` = ?", args.get("name"));
		}

		if (args.get("nickname") != null) {
			needToUpdate = true;
			sql.append(", nickname = ?", args.get("nickname"));
		}

		if (args.get("email") != null) {
			needToUpdate = true;
			sql.append(", email = ?", args.get("email"));
		}

		if (args.get("cellphoneNo") != null) {
			needToUpdate = true;
			sql.append(", cellphoneNo = ?", args.get("cellphoneNo"));
		}

		if (args.get("authLevel") != null) {
			needToUpdate = true;
			sql.append(", authLevel = ?", args.get("authLevel"));
		}

		if ( needToUpdate == false ) {
			return 0;
		}

		sql.append("WHERE id = ?", args.get("id"));
System.out.println(sql);
		return MysqlUtil.update(sql);
	}
}
