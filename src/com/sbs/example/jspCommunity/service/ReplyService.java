package com.sbs.example.jspCommunity.service;

import java.util.List;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dao.ReplyDao;
import com.sbs.example.jspCommunity.dto.Reply;

public class ReplyService {
	private ReplyDao replyDao;
	
	public ReplyService() {
		replyDao = Container.replyDao;
	}
	
	public List<Reply> getReplys(int id) {
		return replyDao.getReplys(id);
	}

}
