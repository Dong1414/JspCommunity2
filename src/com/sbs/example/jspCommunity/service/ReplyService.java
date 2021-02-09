package com.sbs.example.jspCommunity.service;

import java.util.List;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dao.ReplyDao;
import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.jspCommunity.dto.Reply;

public class ReplyService {
	private ReplyDao replyDao;
	private LikeService likeService;

	public ReplyService() {
		replyDao = Container.replyDao;
		likeService = Container.likeService;
	}

	public List<Reply> getReplys(int id) {
		return getReplys(id, null);
	}

	public List<Reply> getReplys(int id, Member actor) {

		List<Reply> reply = replyDao.getReplys(id);
		if (reply == null) {
			return null;
		}

		if (reply != null) {
			updateInfoForPrint(id, actor, reply);
		}
		return reply;
	}

	private void updateInfoForPrint(int id, Member actor, List<Reply> replys) {

		for (Reply reply : replys) {
			boolean actorCanLike = likeService.actorCanLike("reply", reply.getId(), actor);
			boolean actorCanCancelLike = likeService.actorCanCancelLike("reply", reply.getId(), actor);
			boolean actorCanDislike = likeService.actorCanDislike("reply", reply.getId(), actor);
			boolean actorCanCancelDislike = likeService.actorCanCancelDislike("reply", reply.getId(), actor);
			reply.getExtra().put("actorCanLike", actorCanLike);
			reply.getExtra().put("actorCanCancelLike", actorCanCancelLike);
			reply.getExtra().put("actorCanDislike", actorCanDislike);
			reply.getExtra().put("actorCanCancelDislike", actorCanCancelDislike);
		}
	}

	public void modify(int replyId, String body) {
		replyDao.modify(replyId, body);

	}

}
