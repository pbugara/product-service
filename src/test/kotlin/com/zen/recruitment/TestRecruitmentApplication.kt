package com.zen.recruitment

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<RecruitmentApplication>().with(TestcontainersConfiguration::class).run(*args)
}
