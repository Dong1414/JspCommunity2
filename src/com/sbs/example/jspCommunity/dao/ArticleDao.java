package com.sbs.example.jspCommunity.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.dto.Board;
import com.sbs.example.jspCommunity.dto.Reply;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class ArticleDao {
	
	public List<Article> getForPrintArticlesByBoardId(int boardId, int limitStart, int limitCount, String searchKeywordType, String searchKeyword) {
		List<Article> articles = new ArrayList<>();
		String article = "article";
		SecSql sql = new SecSql();
		sql.append("SELECT A.*");
		sql.append(", M.name AS extra__writer");
		sql.append(", M.nickname AS extra__nickname");
		sql.append(", B.name AS extra__boardName");
		sql.append(", B.code AS extra__boardCode");
		sql.append(", IFNULL(SUM(L.point), 0) AS extra__likePoint");
		sql.append(", IFNULL(SUM(IF(L.point > 0, L.point, 0)), 0) AS extra__likeOnlyPoint");
		sql.append(", IFNULL(SUM(IF(L.point < 0, L.point * -1, 0)), 0) extra__dislikeOnlyPoint");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("INNER JOIN `board` AS B");
		sql.append("ON A.boardId = B.id");
		sql.append("LEFT JOIN `like` AS L");
		sql.append("ON L.relTypeCode = 'article'");
		sql.append("AND A.id = L.relId");
		

		
		if (boardId != 0) {
			sql.append("WHERE A.boardId = ?", boardId);
		}
		if (searchKeyword != null) {
			if (searchKeywordType == null || searchKeywordType.equals("title")) {
				sql.append("AND A.title LIKE CONCAT('%', ? '%')", searchKeyword);
			} else if (searchKeywordType.equals("body")) {
				sql.append("AND A.body LIKE CONCAT('%', ? '%')", searchKeyword);
			} else if (searchKeywordType.equals("titleAndBody")) {
				sql.append("AND (A.title LIKE CONCAT('%', ? '%') OR A.body LIKE CONCAT('%', ? '%'))", searchKeyword, searchKeyword);
			}
		}
		sql.append("GROUP BY A.id");
		sql.append("ORDER BY A.id DESC");

		if ( limitCount != -1 ) {
			sql.append("LIMIT ?, ?", limitStart, limitCount);
		}
		

		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);

		for (Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));
		}
		return articles;
	}


	public Article getForPrintArticleById(int id) {
		SecSql sql = new SecSql();
		sql.append("SELECT A.*");
		sql.append(", M.name AS extra__writer");
		sql.append(", B.name AS extra__boardName");
		sql.append(", B.code AS extra__boardCode");
		sql.append(", IFNULL(SUM(L.point), 0) AS extra__likePoint");
		sql.append(", IFNULL(SUM(IF(L.point > 0, L.point, 0)), 0) AS extra__likeOnlyPoint");
		sql.append(", IFNULL(SUM(IF(L.point < 0, L.point * -1, 0)), 0) extra__dislikeOnlyPoint");
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("INNER JOIN `board` AS B");
		sql.append("ON A.boardId = B.id");
		sql.append("LEFT JOIN `like` AS L");
		sql.append("ON L.relTypeCode = 'article'");
		sql.append("AND A.id = L.relId");
		sql.append("WHERE A.id = ?", id);
		sql.append("GROUP BY A.id");

		Map<String, Object> map = MysqlUtil.selectRow(sql);

		if (map.isEmpty()) {
			return null;
		}

		return new Article(map);
	}

	public Board getBoardById(int id) {
		SecSql sql = new SecSql();
		sql.append("SELECT B.*");
		sql.append("FROM board AS B");
		sql.append("WHERE B.id = ?", id);

		Map<String, Object> map = MysqlUtil.selectRow(sql);

		if (map.isEmpty()) {
			return null;
		}

		return new Board(map);
	}

	public int write(Map<String, Object> args) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", boardId = ?", args.get("boardId"));
		sql.append(", memberId = ?", args.get("memberId"));
		sql.append(", title = ?", args.get("title"));
		sql.append(", `body` = ?", args.get("body"));

		return MysqlUtil.insert(sql);
	}

	public int doDelete(int id) {
		SecSql sql = new SecSql();
		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", id);

		return MysqlUtil.delete(sql);
	}

	public int modify(Map<String, Object> args) {
		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");

		boolean needToUpdate = false;

		if (args.get("title") != null) {
			needToUpdate = true;
			sql.append(", title = ?", args.get("title"));
		}

		if (args.get("body") != null) {
			needToUpdate = true;
			sql.append(", `body` = ?", args.get("body"));
		}

		if (needToUpdate == false) {
			return 0;
		}

		sql.append("WHERE id = ?", args.get("id"));

		return MysqlUtil.update(sql);
	}

	public int getArticlesCountByBoardId(int boardId, String searchKeywordType, String searchKeyword) {
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(*) AS cnt");
		sql.append("FROM article AS A");
		sql.append("WHERE 1");
		if (boardId != 0) {
			sql.append("AND A.boardId = ?", boardId);
		}
		if (searchKeyword != null) {
			if (searchKeywordType == null || searchKeywordType.equals("title")) {
				sql.append("AND A.title LIKE CONCAT('%', ? '%')", searchKeyword);
			} else if (searchKeywordType.equals("body")) {
				sql.append("AND A.body LIKE CONCAT('%', ? '%')", searchKeyword);
			} else if (searchKeywordType.equals("titleAndBody")) {
				sql.append("AND (A.title LIKE CONCAT('%', ? '%') OR A.body LIKE CONCAT('%', ? '%'))", searchKeyword, searchKeyword);
			}
		}
		return MysqlUtil.selectRowIntValue(sql);
	}


	public void updateHit(int id) {
		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET hitsCount = hitsCount + 1");
		sql.append("WHERE id = ?", id);
		
		MysqlUtil.update(sql);
	}


	public int getLikeCount(int articleId) {
		String article = "article";
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(`point`) AS COUNT");
		sql.append("FROM `like`");
		sql.append("WHERE relId = ?", articleId);
		sql.append("AND relTypeCode = ?",article);
		sql.append("AND `point` = 1");
		Map<String, Object> map = MysqlUtil.selectRow(sql);
		int count = 0;
		
		if(null == map.get("COUNT") || map.isEmpty()) {
			return count;
		}
		count = Integer.parseInt(String.valueOf(map.get("COUNT")));		
		return count;
	}
	public int getHateCount(int articleId) {
		String article = "article";
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(`point`) AS COUNT");
		sql.append("FROM `like`");
		sql.append("WHERE relId = ?", articleId);
		sql.append("AND relTypeCode = ?",article);
		sql.append("AND `point` = -1");
		Map<String, Object> map = MysqlUtil.selectRow(sql);
		int count = 0;
		
		if(null == map.get("COUNT") || map.isEmpty()) {
			return count;
		}
		count = Integer.parseInt(String.valueOf(map.get("COUNT")));		
		return count;
	}

	public int likeUp(int memberId, int articleId) {
		SecSql sql1 = new SecSql();
		String article = "article";
		sql1.append("SELECT *");
		sql1.append("FROM `like`");
		sql1.append("WHERE relId = ?",articleId);
		sql1.append("AND relTypeCode = ?",article);
		sql1.append("AND memberId = ?", memberId);
		sql1.append("AND `point` = 1");		
		Map<String, Object> map = MysqlUtil.selectRow(sql1);		
		if(!map.isEmpty()) {
			return 0;
		}
		
		SecSql sql = new SecSql();
		
		sql.append("INSERT INTO `like`");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", relTypeCode = ?",article);
		sql.append(", relId = ?", articleId);
		sql.append(", memberId = ?", memberId);
		sql.append(", `point` = 1");
				
		return MysqlUtil.insert(sql);		
	}


	public int hateUp(int memberId, int articleId) {
		SecSql sql1 = new SecSql();
		String article = "article";
		sql1.append("SELECT *");
		sql1.append("FROM `like`");
		sql1.append("WHERE relId = ?",articleId);
		sql1.append("AND relTypeCode = ?",article);
		sql1.append("AND memberId = ?", memberId);
		sql1.append("AND `point` = 0");
				
		Map<String, Object> map = MysqlUtil.selectRow(sql1);		
		if(!map.isEmpty()) {
			return 0;
		}
		
		SecSql sql = new SecSql();
		
		sql.append("INSERT INTO `like`");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()");
		sql.append(", relTypeCode = ?",article);
		sql.append(", relId = ?", articleId);
		sql.append(", memberId = ?", memberId);
		sql.append(", `point` = 0");
				
		return MysqlUtil.insert(sql);	
	}

	public boolean likeCheck(int articleId, int memberId) {
		SecSql sql1 = new SecSql();
		String article = "article";
		sql1.append("SELECT *");
		sql1.append("FROM `like`");
		sql1.append("WHERE relId = ?",articleId);
		sql1.append("AND relTypeCode = ?",article);
		sql1.append("AND memberId = ?", memberId);
		sql1.append("AND `point` = 1");
		
		Map<String, Object> map = MysqlUtil.selectRow(sql1);		
		if(!map.isEmpty()) {
			return true;
		}
		return false;
	}


	public boolean hateCheck(int articleId, int memberId) {
		SecSql sql1 = new SecSql();
		String article = "article";
		sql1.append("SELECT *");
		sql1.append("FROM `like`");
		sql1.append("WHERE relId = ?",articleId);
		sql1.append("AND relTypeCode = ?",article);
		sql1.append("AND memberId = ?", memberId);
		sql1.append("AND `point` = 0");
		
		Map<String, Object> map = MysqlUtil.selectRow(sql1);		
		if(!map.isEmpty()) {
			return true;
		}
		return false;
	}


	public int likeDown(int memberId, int articleId) {
		SecSql sql = new SecSql();
		String article = "article";
		sql.append("DELETE FROM `like`");
		sql.append("WHERE relId = ?",articleId);
		sql.append("AND relTypeCode = ?",article);
		sql.append("AND memberId = ?", memberId);
		sql.append("AND `point` = 1");
		
		return MysqlUtil.delete(sql); 
	}


	public int hateDown(int memberId, int articleId) {
		SecSql sql = new SecSql();
		String article = "article";
		sql.append("DELETE FROM `like`");
		sql.append("WHERE relId = ?",articleId);
		sql.append("AND relTypeCode = ?",article);
		sql.append("AND memberId = ?", memberId);
		sql.append("AND `point` = 0");
		
		return MysqlUtil.delete(sql); 
	}

	public Article getArticleById(int id) {
		SecSql sql = new SecSql();
		sql.append("SELECT A.*");
		sql.append("FROM article AS A");
		sql.append("WHERE A.id = ?", id);

		Map<String, Object> map = MysqlUtil.selectRow(sql);

		if (map.isEmpty()) {
			return null;
		}

		return new Article(map);
	}
}
