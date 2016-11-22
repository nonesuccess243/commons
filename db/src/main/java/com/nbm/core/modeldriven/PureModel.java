package com.nbm.core.modeldriven;

import java.io.Serializable;

import com.nbm.core.modeldriven.anno.NotNull;

/**
 * @see Model
 * 
 * 之前只有一个Model，但考虑不周，那个Model中增加的几个字段为数据库结构的变更增添了好多困难，所以就增加了这个类。
 * 
 * 需要假删除字段的，就用Model作为父类，不需要的，就用现在这个类。
 * @author niyuzhe
 *
 */
public abstract class PureModel implements Serializable ,Cloneable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Long id;
	
	public final Long getId()
	{
		return id;
	}

	public final void setId(Long id)
	{
		this.id = id;
	}


}
