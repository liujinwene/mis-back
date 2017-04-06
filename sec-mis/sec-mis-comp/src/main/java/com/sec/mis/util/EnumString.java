package com.sec.mis.util;



public class EnumString {

	private EnumString(){}
	
	 
	public static String getIdTypeD(int idType) {
		switch (idType) {
			default:
			case 0:		return "身份证";
			case 1:		return "护照";
			case 2:		return "军官证";
		}
	}
	
	public static String getKidCountD(int kidCount) {
		switch (kidCount) {
			default:
			case 0:		return "无";
			case 1:		return "1个";
			case 2:		return "2";
			case 3:		return "3";
			case 4:		return "4";
			case 5:		return "4个以上";
		}
	}
	
	public static String getHasOrNot(int isHas) {
		switch (isHas) {
		default:
		case 0:		return "无";
		case 1:		return "有";
		}
	}
	
   
	
	public static String getStuffTypeD(int type) {
		switch (type) {
			default:
			case 0:return "其它";
			case 1:return "本人身份证";
			case 2:return "家人身份证";
			case 3:return "户口证明(户主和本人页)";
			case 4:return "行驶证";
			case 5:return "驾驶证";
			case 6:return "毕业证";
			case 7:return "生活照";
			case 8:return "手机通话清单(最近三个月)";
			case 9:return "居住地水、电、燃气缴费单(最单近三个月)";
			case 10:return "固定电话清单(最近三个月)";
			case 11:return "户口证明(家人页)";
			case 12:return "婚姻证明";
			case 13:return "工作证明";
			case 14:return "个人银行流水(最近6个月)";
			case 15:return "房产证(按揭合同)";
			case 16:return "住址证明(租赁合同、房租交费单)";
			case 17:return "个人银行征信报告";
			case 18:return "信用卡账单";
			case 19:return "社保卡";
			case 20:return "组织机构代码";
			case 21:return "营业执照";
			case 22:return "税务登记证";
			case 23:return "借款承诺书";
			case 24:return "居住证";
			case 25:return "家人信用报告";
			case 26:return "车辆登记证";
			case 27:return "车辆保险单";
			case 28:return "银行开户凭证(附贷款卡)";
			case 29:return "公司章程";
			case 30:return "验资报告";
			case 31:return "财务报表(三年利润表和资产负债表)";
			case 32:return "公司流水(半年)";
			case 33:return "公司办公场所租赁合同";
			case 34:return "公司购销合同或合作协议";
			case 35:return "财产(汽车、股票、基金、机械设备等)";
			case 36:return "借款担保合同";
			case 37:return "抵押合同";
		}
	}

	public static String getGroupClassifiedString(int classified) {
		switch (classified) {
			default:
			case 0:	return "投资理财";
			case 1:	return "新闻时事";
			case 2:	return "同城交友";
			case 3:	return "娱乐休闲";
			case 4:	return "个人原创";
			case 5:	return "体育天地";
			case 6:	return "情感部落";
			case 7:	return "时尚生活";
			case 8:	return "灌水八卦";
			case 9:	return "其它";
		}
	}
  
	public static String getBorrowGradeString(int grade) {
		switch (grade) {
			default:
			case 0:return "F";
			case 1:return "E";
			case 2:return "D";
			case 3:return "C";
			case 4:return "B";
			case 5:return "A";
		}
	}
	 
	public static String getRechargeBank(String bankCode) {
		if(bankCode.equals("CCB"))			return "中国建设银行";
		else if(bankCode.equals("CMB")) 	return "招商银行";
		else if(bankCode.equals("ICBC")) 	return "中国工商银行";
		else if(bankCode.equals("BOC")) 	return "中国银行";
		else if(bankCode.equals("ABC")) 	return "中国农业银行";
		else if(bankCode.equals("BOCOM")) 	return "交通银行";
		else if(bankCode.equals("CMBC"))	return "中国民生银行";
		else if(bankCode.equals("HXBC"))	return "华夏银行";
		else if(bankCode.equals("CIB")) 	return "兴业银行";
		else if(bankCode.equals("SPDB")) 	return "上海浦东发展银行";
		else if(bankCode.equals("GDB"))		return "广东发展银行";
		else if(bankCode.equals("CITIC")) 	return "中信银行";
		else if(bankCode.equals("CEB")) 	return "光大银行";
		else if(bankCode.equals("PSBC")) 	return "中国邮政储蓄银行";
		else if(bankCode.equals("PAYH")) 	return "平安银行";
		return "";//In case
	}
	
 
	
	public static String getBankName(int bankType) {
		switch (bankType) {
			default:
			case 0:	return "其他";
			case 1:return "中国工商银行";
			case 2:return "中国农业银行";
			case 3:return "中国建设银行";
			case 4:return "中国招商银行";
			case 5:return "中国民生银行";
			case 6:return "中国交通银行";
			case 7:return "中国银行";
			case 8:return "中信银行";
			case 9:return "广发银行";
			case 10:return "兴业银行";
			case 11:return "华夏银行";
			case 12:return "上海银行";
			case 13:return "北京银行";
			case 14:return "光大银行";
			case 15:return "平安银行";
			case 18:return "南京银行";
			case 20:return "中国邮政储蓄银行";
			case 21:return "上海浦东发展银行";
		}
	}
	
	public static String getBank_Code(int bankType) {
		switch (bankType) {
			default:
			case 0:	return "其他";
			case 1:return "01020000";
			case 2:return "01030000";
			case 3:return "01050000";
			case 4:return "03080000";
			case 5:return "03050000";
			case 6:return "03010000";
			case 7:return "01040000";
			case 8:return "03020000";
			case 9:return "03060000";
			case 10:return "03090000";
			case 11:return "03040000";
			case 12:return "04012900";
			case 13:return "04031000";
			case 14:return "03030000";
			case 15:return "03070000";
			case 18:return "04243010";
			case 20:return "01000000";
			case 21:return "03100000";
		}
	}
	
	public static String getBankCode(int bankType) {
		switch (bankType) {
			default:
			case 0:	return "其他";
			case 1:return "bank-icbc";
			case 2:return "bank-nyyh";
			case 3:return "bank-jsyh";
			case 4:return "bank-zsyh";
			case 5:return "bank-msyh";
			case 6:return "bank-jtyh";
			case 7:return "bank-boc";
			case 8:return "bank-zxyh";
			case 9:return "bank-cgb";
			case 10:return "bank-xyyh";
			case 11:return "bank-hxyh";
			case 12:return "bank-shyh";
			case 13:return "bank-bjyh";
			case 14:return "bank-gdyh";
			case 15:return "bank-ping";
			case 16:return "中国信合";
			case 17:return "广州银行";
			case 18:return "bank-njyh";
			case 19:return "深圳发展银行";
			case 20:return "bank-yzcx";
			case 21:return "bank-pfyh";
		}
	}	
	 
	public static String getBidRequestPurposeType(int purposeType) {
		switch (purposeType) {
			default:
			case 0:return "创业借款";
			case 1:return "教育培训借款";
			case 2:return "结婚消费借款";
			case 3:return "购买家具家电";
			case 4:return "住房装修借款";
			case 5:return "旅游消费贷款";
			case 6:return "汽车消费借款";
			case 7:return "短期周转借款";
			case 8:return "其他用途贷款";
		}
	}
	  
	public static String getSexD(int sex) {
		switch (sex) {
			default:
			case 0:	return "男";
			case 1:	return "女";
		}
	}
	
	public static String getMarriageString(int marriageState) {
		switch (marriageState) {
			default:
			case 0:	return "已婚";
			case 1:	return "未婚";
			case 2:	return "离异";
			case 3:	return "再婚";
			case 4:	return "丧偶";
		}
	}
	
	public static String getChildrenString(int marriageState) {
		switch (marriageState) {
		default:
		case 0:	return "无子女";
		case 1:	return "未成年";
		case 2:	return "已成年";
		}
	}
	
	public static String getCarInfoString(int carInfoState) {
		switch (carInfoState) {
		default:
		case 0:	return "无车产";
		case 1:	return "有车产";
		}
	}
	
	public static String getIncomeGradeString(int incomeGrade) {
		switch (incomeGrade) {
			default:
			case 0:	return "1000元以下";
			case 1:	return "1000-2000元";
			case 2:	return "2000-3000元";
			case 3:	return "3000-4000元";
			case 4:	return "4000-5000元";
			case 5:	return "5000-8000元";
			case 6:	return "8000-10000元";
			case 7:	return "10000-30000元";
			case 8:	return "30000-50000元";
			case 9:	return "50000元以上";
		}
	}
	
	public static String getSocialRoleString(int grade) {
		switch (grade) {
			default:
			case 0:	return "公务员";
			case 1:	return "公司职员(白领)";
			case 2:	return "公司法人代表";
			case 3:	return "专职网店卖家";
			case 4:	return "私营业主";
			case 5:	return "农民";
			case 6:	return "务工人员";
			case 7:	return "学生";
			case 8:	return "退休人员";
			case 9:	return "兼职";
			case 10:return "自由职业者";
			case 11:return "其他";
		}
	}
	
	public static String getEducationBackgroundD(int educationBackground) {
		switch (educationBackground) {
		    default:
			case 0:	return "文盲";
			case 1:	return "小学";
			case 2:	return "初中";
			case 3:	return "高中";
			case 4:	return "本科";
			case 5:	return "硕士研究生";
			case 6:	return "博士研究生";
			case 7:	return "专科";
			case 8:	return "其它";
		}
	}
	
	public static String getHouseConditionD(int houseCondition) {
		switch (houseCondition) {
			default:return "其他";
			case 0:	return "有房产";
			case 1:	return "无房产";
		}
	}

	public static String getContactPersonRelationD(int contactPersonRelation) {
		switch (contactPersonRelation) {
			default:
			case 0:		return "配偶";
			case 1:		return "母亲";
			case 2:		return "父亲";
			case 3:		return "子女";
			case 4:		return "兄弟姐妹";
			case 10:	return "父亲";
			case 11:	return "母亲";
			case 12:	return "配偶";
			case 13:	return "兄弟姐妹";
			case 14:	return "其他";
		}
	}
	
	public static String getCompanyTypeD(int companyType) {
		switch (companyType) {
			default:
			case 0:	return "政府机关";
			case 1:	return "国有企业";
			case 2:	return "国内私营企业";
			case 3:	return "台(港、澳)资企业";
			case 4:	return "外资企业";
			case 5:	return "合资企业";
			case 6:	return "个体户";
			case 7:	return "事业性单位";
		}
	}
	
	public static String getCompanyAreaString(int companyArea) {
		switch (companyArea) {
			default:
			case 0:	return "农、林、牧、渔业";
			case 1:	return "制造业";
			case 2:	return "电力、燃气及水的生产和供应业";
			case 3:	return "建筑业";
			case 4:	return "交通运输、仓储和邮政业";
			case 5:	return "信息传输、计算机服务和软件业";
			case 6:	return "批发和零售业";
			case 7:	return "金融业";
			case 8:	return "房地产业";
			case 9:	return "采矿业";
			case 10:return "租赁和商务服务业";
			case 11:return "科学研究、技术服务和地质勘查业";
			case 12:return "水利、环境和公共设施管理业";
			case 13:return "居民服务和其他服务业";
			case 14:return "教育";
			case 15:return "卫生、社会保障和社会福利业";
			case 16:return "文化、体育和娱乐业";
			case 17:return "公共管理和社会组织";
			case 18:return "国际组织";
		}
	}

	public static String getCompanySizeString(int companySize) {
		switch (companySize) {
			default:
			case 0:	return "10人以下";
			case 1:	return "10-50人";
			case 2:	return "50-100人";
			case 3:	return "100-500人";
			case 4:	return "500人以上";
		}
	}
	
	public static String getCompanyPositionString(int companyPosition) {
		switch (companyPosition) {
			default:
			case 0:	return "普通职员";
			case 1:	return "工程师";
			case 2:	return "高级工程师";
			case 3:	return "主管";
			case 4:	return "部门级领导人";
			case 5:	return "总经理级以上";
			case 6:	return "高校学生";
			case 7:	return "高校老师";
		}
	}
	
	public static String getYearCountString(int yearCount) {
		switch (yearCount) {
			default:
			case 0:	return "一年以下";
			case 1:	return "一年以上";
			case 2:	return "二年以上";
			case 3:	return "三年以上";
			case 4:	return "四年以上";
			case 5:	return "五年以上";
			case 6:	return "六年以上";
		}
	}
	
	public static String getPlace(int code) {
		switch (code) {
			default:
			case 110100:return "北京";
			case 310100:return "上海";
			case 120100:return "天津";
			case 500100:return "重庆";
			case 100000:return "香港";
			case 200000:return "澳门";
			case 340000:return "安徽";
			case 340100:return "合肥";
			case 340800:return "安庆";
			case 340300:return "蚌埠";
			case 341400:return "巢湖";
			case 341700:return "池洲";
			case 341100:return "滁洲";
			case 341600:return "亳州";
			case 341200:return "阜阳";
			case 340600:return "淮北";
			case 340400:return "淮南";
			case 341000:return "黄山";
			case 341500:return "六安";
			case 340500:return "马鞍山";
			case 341300:return "宿州";
			case 340700:return "铜陵";
			case 340200:return "芜湖";
			case 341800:return "宣城";
			case 350000:return "福建";
			case 350100:return "福州";
			case 350800:return "龙岩";
			case 350700:return "南平";
			case 350900:return "宁德";
			case 350300:return "莆田";
			case 350500:return "泉州";
			case 350400:return "三明";
			case 350200:return "厦门";
			case 350600:return "漳州";
			case 620000:return "甘肃";
			case 620100:return "兰州";
			case 620400:return "白银";
			case 621100:return "定西";
			case 623000:return "甘南藏族自治州";
			case 620200:return "嘉峪关";
			case 620300:return "金昌";
			case 620900:return "酒泉";
			case 622900:return "临夏回族自治州";
			case 621200:return "陇南地区";
			case 620800:return "平凉";
			case 621000:return "庆阳";
			case 620500:return "天水";
			case 620600:return "武威";
			case 620700:return "张掖";
			case 440000:return "广东";
			case 440100:return "广州";
			case 445100:return "潮州";
			case 441900:return "东莞";
			case 440600:return "佛山";
			case 441600:return "河源";
			case 441300:return "惠州";
			case 440700:return "江门";
			case 445200:return "揭阳";
			case 440900:return "茂名";
			case 441400:return "梅州";
			case 441800:return "清远";
			case 440500:return "汕头";
			case 441500:return "汕尾";
			case 440200:return "韶关";
			case 440300:return "深圳";
			case 441700:return "阳江";
			case 445300:return "云浮";
			case 440800:return "湛江";
			case 441200:return "肇庆";
			case 442000:return "中山";
			case 440400:return "珠海";
			case 450000:return "广西";
			case 450100:return "南宁";
			case 451000:return "百色";
			case 450500:return "北海";
			case 451400:return "崇左";
			case 450600:return "防城港";
			case 450800:return "贵港";
			case 450300:return "桂林";
			case 451200:return "河池";
			case 451100:return "贺州";
			case 451300:return "来宾";
			case 450200:return "柳州";
			case 450700:return "钦州";
			case 450400:return "梧州";
			case 450900:return "玉林";
			case 520000:return "贵州";
			case 520100:return "贵阳";
			case 520400:return "安顺";
			case 522400:return "毕节地区";
			case 520200:return "六盘水";
			case 522600:return "黔东南苗族侗族自治州";
			case 522700:return "黔南布依族苗族自治州";
			case 522300:return "黔西南布依族苗族自治州";
			case 522200:return "铜仁地区";
			case 520300:return "遵义";
			case 460000:return "海南";
			case 460100:return "海口";
			case 460200:return "三亚";
			case 469000:return "琼海";
			case 460300:return "琼山";
			case 460400:return "儋州";
			case 460500:return "文昌";
			case 460600:return "万宁";
			case 460700:return "五指山";
			case 460800:return "东方";
			case 460900:return "屯昌县";
			case 461000:return "琼中黎族苗族自治县";
			case 130000:return "河北";
			case 130100:return "石家庄";
			case 130600:return "保定";
			case 130900:return "沧州";
			case 130800:return "承德";
			case 130400:return "邯郸";
			case 131100:return "衡水";
			case 131000:return "廊坊";
			case 130300:return "秦皇岛";
			case 130200:return "唐山";
			case 130500:return "邢台";
			case 130700:return "张家口";
			case 410000:return "河南";
			case 410100:return "郑州";
			case 410500:return "安阳";
			case 410600:return "鹤壁";
			case 410800:return "焦作";
			case 410200:return "开封";
			case 410300:return "洛阳";
			case 411100:return "漯河";
			case 411300:return "南阳";
			case 410400:return "平顶山";
			case 410900:return "濮阳";
			case 411200:return "三门峡";
			case 411400:return "商丘";
			case 410700:return "新乡";
			case 411500:return "信阳";
			case 411000:return "许昌";
			case 411600:return "周口";
			case 411700:return "驻马店";
			case 230000:return "黑龙江";
			case 230100:return "哈尔滨";
			case 230600:return "大庆";
			case 232700:return "大兴安岭地区";
			case 230400:return "鹤岗";
			case 231100:return "黑河";
			case 230300:return "鸡西";
			case 230800:return "佳木斯";
			case 231000:return "牡丹江";
			case 230900:return "七台河";
			case 230200:return "齐齐哈尔";
			case 230500:return "双鸭山";
			case 231200:return "绥化";
			case 230700:return "伊春";
			case 420000:return "湖北";
			case 420100:return "武汉";
			case 420700:return "鄂州";
			case 422800:return "恩施土家族苗族自治区";
			case 421100:return "黄冈";
			case 420200:return "黄石";
			case 420800:return "荆门";
			case 421000:return "荆州";
			case 429005:return "潜江";
			case 429021:return "神农架林区";
			case 420300:return "十堰";
			case 421300:return "随州";
			case 429006:return "天门";
			case 429004:return "仙桃";
			case 421200:return "咸宁";
			case 420600:return "襄樊";
			case 420900:return "孝感";
			case 420500:return "宜昌";
			case 430000:return "湖南";
			case 430100:return "长沙";
			case 430700:return "常德";
			case 431000:return "郴州";
			case 430400:return "衡阳";
			case 431200:return "怀化";
			case 431300:return "娄底";
			case 430500:return "邵阳";
			case 430300:return "湘潭";
			case 433100:return "湘西土家族苗族自治区";
			case 430900:return "益阳";
			case 431100:return "永州";
			case 430600:return "岳阳";
			case 430800:return "张家界";
			case 430200:return "株洲";
			case 220000:return "吉林";
			case 220100:return "长春";
			case 220800:return "白城";
			case 220600:return "白山";
			case 220200:return "吉林";
			case 220400:return "辽源";
			case 220300:return "四平";
			case 220700:return "松原";
			case 220500:return "通化";
			case 222400:return "延边朝鲜族自治州";
			case 320000:return "江苏";
			case 320100:return "南京";
			case 320400:return "常州";
			case 320800:return "淮安";
			case 320700:return "连云港";
			case 320600:return "南通";
			case 320500:return "苏州";
			case 321300:return "宿迁";
			case 321200:return "泰州";
			case 320200:return "无锡";
			case 320300:return "徐州";
			case 320900:return "盐城";
			case 321000:return "扬州";
			case 321100:return "镇江";
			case 360000:return "江西";
			case 360100:return "南昌";
			case 361000:return "抚州";
			case 360700:return "赣州";
			case 360800:return "吉安";
			case 360200:return "景德镇";
			case 360400:return "九江";
			case 360300:return "萍乡";
			case 361100:return "上饶";
			case 360500:return "新余";
			case 360900:return "宜春";
			case 360600:return "鹰潭";
			case 210000:return "辽宁";
			case 210100:return "沈阳";
			case 210300:return "鞍山";
			case 210500:return "本溪";
			case 211300:return "朝阳";
			case 210200:return "大连";
			case 210600:return "丹东";
			case 210400:return "抚顺";
			case 210900:return "阜新";
			case 211400:return "葫芦岛";
			case 210700:return "锦州";
			case 211000:return "辽阳";
			case 211100:return "盘锦";
			case 211200:return "铁岭";
			case 210800:return "营口";
			case 150000:return "内蒙古";
			case 150100:return "呼和浩特";
			case 152900:return "阿拉善盟";
			case 150800:return "巴彦淖尔";
			case 150200:return "包头";
			case 150400:return "赤峰";
			case 150600:return "鄂尔多斯";
			case 150700:return "呼伦贝尔";
			case 150500:return "通辽";
			case 150300:return "乌海";
			case 150900:return "乌兰察布";
			case 152500:return "锡林郭勒盟";
			case 152200:return "兴安盟";
			case 640000:return "宁夏";
			case 640100:return "银川";
			case 640400:return "固原";
			case 640200:return "石嘴山";
			case 640300:return "吴忠";
			case 640500:return "中卫";
			case 630000:return "青海";
			case 630100:return "西宁";
			case 632600:return "果洛藏族自治州";
			case 632200:return "海北藏族自治州";
			case 632100:return "海东地区";
			case 632500:return "海南藏族自治州";
			case 632800:return "海西蒙古族藏族自治州";
			case 632300:return "黄南藏族自治州";
			case 632700:return "玉树藏族自治州";
			case 370000:return "山东";
			case 370100:return "济南";
			case 371600:return "滨州";
			case 371400:return "德州";
			case 370500:return "东营";
			case 371700:return "菏泽";
			case 370800:return "济宁";
			case 371200:return "莱芜";
			case 371500:return "聊城";
			case 371300:return "临沂";
			case 370200:return "青岛";
			case 371100:return "日照";
			case 370900:return "泰安";
			case 371000:return "威海";
			case 370700:return "潍坊";
			case 370600:return "烟台";
			case 370400:return "枣庄";
			case 370300:return "淄博";
			case 140000:return "山西";
			case 140100:return "太原";
			case 140400:return "长治";
			case 140200:return "大同";
			case 140500:return "晋城";
			case 140700:return "晋中";
			case 141000:return "临汾";
			case 141100:return "吕梁";
			case 140600:return "朔州";
			case 140900:return "忻州";
			case 140300:return "阳泉";
			case 140800:return "运城";
			case 610000:return "陕西";
			case 610100:return "西安";
			case 610900:return "安康";
			case 610300:return "宝鸡";
			case 610700:return "汉中";
			case 611000:return "商洛";
			case 610200:return "铜川";
			case 610500:return "渭南";
			case 610400:return "咸阳";
			case 610600:return "延安";
			case 610800:return "榆林";
			case 510000:return "四川";
			case 510100:return "成都";
			case 513200:return "阿坝藏族羌族自治州";
			case 511900:return "巴中";
			case 511700:return "达州";
			case 510600:return "德阳";
			case 513300:return "甘孜藏族自治州";
			case 511600:return "广安";
			case 510800:return "广元";
			case 511100:return "乐山";
			case 513400:return "凉山彝族自治州";
			case 510500:return "泸州";
			case 511400:return "眉山";
			case 510700:return "绵阳";
			case 511300:return "南充";
			case 511000:return "内江";
			case 510400:return "攀枝花";
			case 510900:return "遂宁";
			case 511800:return "雅安";
			case 511500:return "宜宾";
			case 512000:return "资阳";
			case 510300:return "自贡";
			case 540000:return "西藏";
			case 540100:return "拉萨";
			case 542500:return "阿里地区";
			case 542100:return "昌都地区";
			case 542600:return "林芝地区";
			case 542400:return "那曲地区";
			case 542300:return "日喀则地区";
			case 542200:return "山南地区";
			case 650000:return "新疆";
			case 650100:return "乌鲁木齐";
			case 652900:return "阿克苏地区";
			case 654300:return "阿勒泰地区";
			case 652800:return "巴音郭楞蒙古自治州";
			case 652700:return "博尔塔拉蒙古自治州";
			case 652300:return "昌吉回族自治州";
			case 652200:return "哈密地区";
			case 653200:return "和田地区";
			case 653100:return "喀什地区";
			case 650200:return "克拉玛依";
			case 653000:return "克孜勒苏柯尔克孜自治州";
			case 650300:return "石河子";
			case 654200:return "塔城地区";
			case 652100:return "吐鲁番地区";
			case 654000:return "伊犁哈萨克自治州";
			case 530000:return "云南";
			case 530100:return "昆明";
			case 530500:return "保山";
			case 532300:return "楚雄彝族自治州";
			case 532900:return "大理白族自治州";
			case 533100:return "德宏傣族景颇族自治州";
			case 533400:return "迪庆藏族自治州";
			case 532500:return "红河哈尼族彝族自治州";
			case 530700:return "丽江";
			case 530900:return "临沧";
			case 533300:return "怒江傈僳族自治州";
			case 530800:return "普洱";
			case 530300:return "曲靖";
			case 532600:return "文山壮族苗族自治州";
			case 532800:return "西双版纳傣族自治州";
			case 530400:return "玉溪";
			case 530600:return "昭通";
			case 330000:return "浙江";
			case 330100:return "杭州";
			case 330500:return "湖州";
			case 330400:return "嘉兴";
			case 330700:return "金华";
			case 331100:return "丽水";
			case 330200:return "宁波";
			case 330800:return "衢州";
			case 330600:return "绍兴";
			case 331000:return "台州";
			case 330300:return "温州";
			case 330900:return "舟山";
			case 660000:return "香港";
			case 670000:return "澳门";
		}
	}
	
	public static String getNationalityD(int code) {
		switch (code) {
			default:
			case 1:return "汉族";
			case 2:return "回族";
			case 3:return "黎族";
			case 4:return "阿昌族";
			case 5:return "白族";
			case 6:return "保安族";
			case 7:return "布朗族";
			case 8:return "布依族";
			case 9:return "朝鲜族";
			case 10:return "达斡尔族";
			case 11:return "傣族";
			case 12:return "德昂族";
			case 13:return "东乡族";
			case 14:return "侗族";
			case 15:return "独龙族";
			case 16:return "俄罗斯族";
			case 17:return "鄂伦春族";
			case 18:return "鄂温克族";
			case 19:return "高山族";
			case 20:return "仡佬族";
			case 21:return "哈尼族";
			case 22:return "哈萨克族";
			case 23:return "赫哲族";
			case 24:return "基诺族";
			case 25:return "京族";
			case 26:return "景颇族";
			case 27:return "柯尔克孜族";
			case 28:return "拉祜族";
			case 29:return "傈僳族";
			case 30:return "珞巴族";
			case 31:return "满族";
			case 32:return "毛南族";
			case 33:return "门巴族";
			case 34:return "蒙古族";
			case 35:return "苗族";
			case 36:return "仫佬族";
			case 37:return "纳西族";
			case 38:return "怒族";
			case 39:return "普米族";
			case 40:return "羌族";
			case 41:return "撒拉族";
			case 42:return "畲族";
			case 43:return "水族";
			case 44:return "塔吉克族";
			case 45:return "塔塔尔族";
			case 46:return "土家族";
			case 47:return "土族";
			case 48:return "佤族";
			case 49:return "维吾尔族";
			case 50:return "乌孜别克族";
			case 51:return "锡伯族";
			case 52:return "瑶族";
			case 53:return "彝族";
			case 54:return "裕固族";
			case 55:return "藏族";
			case 56:return "壮族";  
		}
	}
}
