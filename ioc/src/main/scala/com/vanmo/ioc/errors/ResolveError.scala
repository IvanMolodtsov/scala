package com.vanmo.ioc.errors

class ResolveError(message: String, cause: Option[Throwable] = None) extends Error(message, cause.orNull)
