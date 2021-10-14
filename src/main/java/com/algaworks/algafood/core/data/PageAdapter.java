package com.algaworks.algafood.core.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PageAdapter<T> extends PageImpl<T> {

	private static final long serialVersionUID = 1L;

	private Pageable pageable;

	public PageAdapter(Page<T> page, Pageable pageable) {
		super(page.getContent(), pageable, page.getTotalElements());

		this.pageable = pageable;
	}

	@Override
	public Pageable getPageable() {
		return this.pageable;
	}

}
