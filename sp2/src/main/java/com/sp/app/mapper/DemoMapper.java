package com.sp.app.mapper;

import java.sql.SQLException;

import com.sp.app.domain.Demo;

public interface DemoMapper {
	public void insertDemo1(Demo dto) throws SQLException;
	public void insertDemo2(Demo dto) throws SQLException;
	public void insertDemo3(Demo dto) throws SQLException;
}
