package com.younker.waf.utils.date;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.younker.waf.utils.StringUtil;

public enum DateUtils
{
	INSTANCE;

	private final static Logger log = LoggerFactory
			.getLogger(DateUtils.class);

	public int getWeek(DateTime startDate, DateTime endDate)
	{
		if (startDate.isAfter(endDate))
		{
			throw new IllegalArgumentException(
					"startDate不能在endDate之后");
		}

		// 如果weekYear相同，则直接相减
		if (startDate.getWeekyear() == endDate.getWeekyear())
			return endDate.getWeekOfWeekyear()
					- startDate.getWeekOfWeekyear() + 1;

		// 如果weekYear不相同，只差一年
		if (startDate.getWeekyear() == endDate.getWeekyear() - 1)
		{
			// endDate所在星期年的星期数+（startDate所在星期年的总星期数-startDate所在星期年的星期数）
			return endDate.getWeekOfWeekyear()
					+ new DateTime("" + startDate.getYear()
							+ "-12-31")
							.getWeekOfWeekyear()
					- startDate.getWeekOfWeekyear() + 1;

		} else
		// 其他暂不支持
		{
			throw new IllegalArgumentException(
					"结尾日期所在的星期年为"
							+ endDate.getWeekyear()
							+ "起始日期所在的星期年为"
							+ startDate.getWeekyear()
							+ "，结尾日期和起始日期所在的星期年份相差为"
							+ (endDate.getWeekyear() - startDate
									.getWeekyear())
							+ "年，当前只支持一年");
		}

	}

	public int getWeek(Date startDate, Date endDate)
	{
		return getWeek(new DateTime(startDate), new DateTime(endDate));
	}

	/**
	 * 当前支持四种格式：yyyy-MM-dd、yyyyMMdd、yyyy.MM.dd、yyyy/MM/dd
	 * 
	 * 本函数不返回空指针
	 * 
	 * 错误信息的使用方式与logback类似，并自动加入要转换的字符串信息，如第一个参数传入2012；01；01，第二个参数传入：育龄妇女编号为
	 * {}，身份证号为{}，可变参数传入123和456，则实际生成的错误信息为：育龄妇女编号为123，身份证号为456[2012；01；01]
	 * 
	 * @param source
	 *                日期字符串
	 * @param errorMessage
	 * @param errorParam
	 * @return
	 */
	private Date stringToDate(String source, String errorMessage,
			Object... errorParam)
	{
	        source = source.replaceAll(" ", "");//这里的replace，是为了去掉中文空格
		DateTimeFormatter[] dateFormatters =
		{ DateTimeFormat.forPattern("yyyy-MM-dd"),
				DateTimeFormat.forPattern("yyyyMMdd"),
				DateTimeFormat.forPattern("yyyy/MM/dd"),
				DateTimeFormat.forPattern("yyyy.MM.dd") };

		for (DateTimeFormatter formatter : dateFormatters)
		{
			try
			{
				return formatter.parseLocalDate(source)
						.toDate();
			} catch (Exception e)
			{
				continue;
			}
		}

		DateTimeFormatter[] dateTimeFormatters =
		{
				DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"),
				DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S"),
				DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SS"),
				DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS") };
		for (DateTimeFormatter formatter : dateTimeFormatters)
		{
			try
			{
				return formatter.parseDateTime(source).toDate();
			} catch (Exception e)
			{
				continue;
			}
		}
		throw new DateNotInStandardException(source,
				StringUtil.getString(errorMessage, errorParam)
						+ "[" + source + "]");
	}

	/**
	 * 传入空字符串或发生异常时返回空指针
	 * 
	 * @param source
	 * @param errorMessage
	 * @param errorParam
	 * @return
	 */
	public Date stringToDateIgnoreAllExceptions(String source,
			String errorMessage, Object... errorParam)
	{
		if (StringUtils.isEmpty(source))
			return null;
		try
		{
			return stringToDate(source, errorMessage, errorParam);
		} catch (Exception e)
		{
			log.error("日期转换发生异常", e);
			return null;
		}

	}

	/**
	 * 传入空字符串时返回空指针
	 * 
	 * @param source
	 * @param errorMessage
	 * @param errorParam
	 * @return
	 */
	public Date stringToDateIgnoreEmptyString(
			String source, String errorMessage,
			Object... errorParam)
	{
		if (StringUtils.isEmpty(source))
			return null;
		return stringToDate(source, errorMessage, errorParam);
	}

	/**
	 * 传入空字符串时返回空指针
	 * 
	 * @param date
	 * @return
	 */
	public String dateToString(Date date)
	{
		if (date != null )
		{
			return new LocalDate(date).toString();
		}
		return null;
	}
	
	/**
	 * 传入空字符串时返回“”
	 * 只用于育龄妇女验证remark显示
	 * @param date
	 * @return
	 */
	public String dateToStringNoNull(Date date)
	{
	        String str = dateToString(date);
	        if(str==null)
	        {
	                return "";
	        }
	        else
	                return str;
	        
	}
	
	/**
	 * 根据日期计算年龄
	 * @param date
	 * @return
	 */
	public int getAgeByDate(Date date)
	{
	        if(date==null)
	        {
	                return 0;
	        }
	        LocalDate now = new LocalDate();
                LocalDate birthday = new LocalDate(date);
                LocalDate birthdayOfThisYear = new LocalDate(now.getYear(), birthday.getMonthOfYear(),
                                birthday.getDayOfMonth());
                if (now.isAfter(birthdayOfThisYear) || now.isEqual(birthdayOfThisYear))
                {
                        return now.getYear() - birthday.getYear();
                } else
                {
                        return now.getYear() - birthday.getYear() - 1;
                }
	}

	public static void main(String[] args)
	{
		Date date = null;
		System.out.println(DateUtils.INSTANCE.getAgeByDate(date));
	}
}
