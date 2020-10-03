package com.algaworks.algafood.infrastructure.repository.service.report;

class ReportException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ReportException(String message) {
        super(message);
    }

    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }

}
