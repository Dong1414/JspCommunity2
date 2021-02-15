package com.sbs.example.jspCommunity.controller;

import java.util.Comparator;

import com.sbs.example.jspCommunity.dto.Article;

public class MiniComparator implements Comparator<Article> {
	@Override
	public int compare(Article first, Article second) {
		int firtValue = first.getHitsCount();
		int secondValue = second.getHitsCount();
		if (firtValue > secondValue) {
			return -1;
		} else if (firtValue < secondValue) {
			return 1;
		} else {
			return 0;
		}
	}
}
