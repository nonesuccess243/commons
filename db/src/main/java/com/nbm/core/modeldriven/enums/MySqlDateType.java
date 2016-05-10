package com.nbm.core.modeldriven.enums;

import com.nbm.core.modeldriven.DbType;

public enum MySqlDateType
{
	DATE
	{
		@Override
		public DbType getDateTypeName()
		{
			return DbType.DATE;
		}
	},
	TIME
	{
		@Override
		public DbType getDateTypeName()
		{
			return DbType.TIME;
		}
	},
	DATETIME
	{
		@Override
		public DbType getDateTypeName()
		{
			return DbType.DATETIME;
		}
	};

	public abstract DbType getDateTypeName();
}
