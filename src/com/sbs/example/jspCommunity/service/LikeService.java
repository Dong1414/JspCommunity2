package com.sbs.example.jspCommunity.service;


import java.util.List;
import java.util.Map;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dao.LikeDao;
import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.dto.Member;

public class LikeService {
	private LikeDao likeDao;

	public LikeService() {
		likeDao = Container.likeDao;
	}

	public boolean actorCanLike(String relTypeCode, int id, Member actor) {
		return likeDao.getPoint(relTypeCode, id, actor.getId()) == 0;
	}

	public boolean actorCanCancelLike(String relTypeCode, int id, Member actor) {
		return likeDao.getPoint(relTypeCode, id, actor.getId()) > 0;
	}

	public boolean actorCanDislike(String relTypeCode, int id, Member actor) {
		return likeDao.getPoint(relTypeCode, id, actor.getId()) == 0;
	}

	public boolean actorCanCancelDislike(String relTypeCode, int id, Member actor) {
		return likeDao.getPoint(relTypeCode, id, actor.getId()) < 0;
	}
	


	public boolean replyActorCanLike(String relTypeCode, int id, Member actor) {
		return likeDao.getPoint(relTypeCode, id, actor.getId()) == 0;
	}

	public boolean replyActorCanCancelLike(String relTypeCode, int id, Member actor) {
		return likeDao.getPoint(relTypeCode, id, actor.getId()) > 0;
	}

	public boolean replyActorCanDislike(String relTypeCode, int id, Member actor) {
		return likeDao.getPoint(relTypeCode, id, actor.getId()) == 0;
	}

	public boolean replyActorCanCancelDislike(String relTypeCode, int id, Member actor) {
		return likeDao.getPoint(relTypeCode, id, actor.getId()) < 0;
	}
	public void setLikePoint(String relTypeCode, int relId, int actorId, int point) {
		if (point == 0) {
			likeDao.removePoint(relTypeCode, relId, actorId);
		} else {
			likeDao.setPoint(relTypeCode, relId, actorId, point);
		}
	}


}
