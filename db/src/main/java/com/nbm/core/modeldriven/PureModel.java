package com.nbm.core.modeldriven;

import java.io.Serializable;
import java.util.Date;

import com.nbm.core.modeldriven.anno.NotNull;
import com.nbm.core.modeldriven.enums.YesOrNo;

public abstract class PureModel implements Serializable ,Cloneable
{
	private static final long serialVersionUID = 1L;

	@NotNull
	private Long id;
	private Date modelLastUpdateTime;
	private Date modelCreateTime;
	private YesOrNo modelIsDeleted;
	private Date modelDeleteTime;

	public final Long getId()
	{
		return id;
	}

	public final void setId(Long id)
	{
		this.id = id;
	}

	public final Date getModelLastUpdateTime()
	{
		return modelLastUpdateTime;
	}

	public final void setModelLastUpdateTime(Date modelLastUpdateTime)
	{
		this.modelLastUpdateTime = modelLastUpdateTime;
	}

	public final Date getModelCreateTime()
	{
		return modelCreateTime;
	}

	public final void setModelCreateTime(Date modelCreateTime)
	{
		this.modelCreateTime = modelCreateTime;
	}

	public final YesOrNo getModelIsDeleted()
	{
		return modelIsDeleted;
	}

	public final void setModelIsDeleted(YesOrNo modelIsDeleted)
	{
		this.modelIsDeleted = modelIsDeleted;
	}

	public final Date getModelDeleteTime()
	{
		return modelDeleteTime;
	}

	public final void setModelDeleteTime(Date modelDeleteTime)
	{
		this.modelDeleteTime = modelDeleteTime;
	}
}
