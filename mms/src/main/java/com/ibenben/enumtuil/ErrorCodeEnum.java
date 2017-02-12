/**
 * 
 */
package com.ibenben.enumtuil;

import com.ibenben.domain.Merchant;

/**
 * @author sszheng
 *
 */
public enum ErrorCodeEnum {
	
	PAGENOTFOUNT(10000, "请求地址不存在"),
	INTERNERLERROR(10001, "内部服务器错误"),
	
	INSERTFAILED(20001,"添加失败"),
	
	USERNOTEXSIS(30001,"用户不存在"),
	PASSWORDERROR(30002, "用户名密码错误"),
	USERUNLOGIN(30003,"用户未登录或session过期"),
	UNAUTHORIZEDURL(30004,"该用户未被授权"),
	
	PARAMSERROR(42000, "非法参数"),
	PARAMSCOPY(42001,"记录已存在"),
	PARAMSEMPTYERROR(40003, "参数值为空"),
	PARAMSMISSINGERROR(40004,"缺少参数"),
	
	PRODUCTNOTFOUND(50001,"未找到产品"),
	PRODUCTTYPENOTFOUND(50002,"未找到产品类型"),
	CATEGORYNAMEEXIST(50005,"品类名称已存在"),
	TAGNAMEEXIST(50006,"标签名称已存在"),
	PRODUCTAPPLYCODEEXIST(50007,"上新申请编码已存在"),
	PRODUCTNAMEEXIST(50008,"产品名称已存在"),
	PRODUCTCONTAINERRECREATED(50009,"该产品箱规重复"),
	GOODSNAMEEXIST(50010,"goods名重复"),
	DATAERROR(50011,"数据错误"),
	PRODUCTPACKAGINGCANDIDATEEXIST(50012,"候选包装方案已存在,不能替换"),
	DUPLICATEFACILITYDIVISIONRULE(50013, "商品到某区域分仓规则不唯一"),
	DUPLICATEPLATFORMREGIONMAPPING(50014, "平台区域映射不不唯一"),
	OUTREGIONTYPEERROR(50015, "out_region_type 错误"),
	PRODUCTAPPLYNAMEEXIST(50016,"商户上新名称已存在"),
	SUPPLIESCONTAINERNOTEXIST(50017,"耗材箱规不存在"),
	COEFFICIENTNOTFOUND(50018,"未找到系数"),
	PRODUCTMAPPINGNOTFOUND(50019,"未找到相应的成品"),
	MORETHANONEPRODUCTMAPPING(50020,"该原料对应多个成品"),
	PRODUCTPACKAGINGNOTFOUND(50021,"未找到包装方案"),
	MORETHANONEPRODUCTPACKAGING(50022,"多个包装方案"),
	CUSTOMERNAMEEXISTS(50023,"客户名称已存在"),
	MAPPINGEXISTED(50024,"映射关系已存在"),
	GETPOINTFAILED(50025,"获取经纬度失败,请修改地址后重试！"),
	USERFACILITYNOTEXIST(50026,"用户仓库不存在"),
	USERMERCHANTNOTEXIST(50027,"用户商户不存在"),
	PRODUCTHASSETUSEBESTSHIPPING(50028,"该商品已设置为选用最优快递"),
	PRODUCTATTRMISSED(50029,"商品属性缺失"),
	PARTYNAMEEXISTS(51000, "业务组织名称已存在"),
	MERCHANTPARTYNOTSET(51001, "商户业务组织未设置"),
	PARTYNOTTHESAME(51002,"业务组织不同"),
	FROMMERCHANTANDTOMERCHANTSAME(51003,"原商户和目标商户必须不同"),
	
	
	REGIONFINDPROVINCEFAILED(70001,"未找到省"),
	REGIONFINDCITYFAILED(70001,"未找到市"),
	REGIONFINDDISTRICTFAILED(70001,"未找到县"),
	
	UPDATEFAILED(60001,"更新失败"),
	
	UPLOADFAILED(80001,"文件上传失败"),
	UPLOADMORETHANONEFILE(80002,"上传文件超过一个"),
	DELETEIMGFAIL(80003,"删除图片失败"),	
	DELETEFAIL(80004,"删除失败,文件不存在"),
	
	FACILITYNOTFOUND(90001,"未找到仓库"),
	PRODUCTSUPPLIERNOTFOUNTD(90002,"未找到供应商"),
	SHIPPINGNOTFOUND(90003,"未找到快递"),
	FACILITYSHIPPINGNOTFOUND(90004,"未找到仓库快递"),
	MERCHANTNOTFOUND(90005,"未找到商户"),
	FACILITYDISTRICTNOTFOUND(90006,"未找到仓库区域"),
	SHOPNOTFOUND(90007,"未找到店铺"),
	PLATFORMNOTFOUND(90008,"未找到平台"),
	GOODSIDMISS(45001,"缺少参数goods_id"),
	GOOSNAMEMISS(45002,"缺少参数goods_name"),
	SHOPIDMISS(45003,"缺少参数shop_id"),
	PLATFORMIDMISS(45004,"缺少参数platform_id"),
	TOKENMISS(45005,"缺少参数token"),
	TOKENERROR(45006,"token错误"),
	NOMERCHANT(45007,"该shop在wms未建商户档案"),
	SHOPIDSMISS(46001,"缺少参数shop_id"),
	SHOPNAMEMISS(46002,"缺少参数shop_name"),
	TOKENSMISS(46003,"缺少参数token"),
	TOKENSERROR(46004,"token错误"),
	REGIONIDMISS(47001,"缺少参数region_id"),
	REGIONNAMEMISS(47002,"缺少参数region_name"),
	PLATFORMIDSMISS(47003,"缺少参数platform_id"),
	PARENTIDMISS(47004,"缺少参数parent_id"),
	REGIONTOKENSMISS(47006,"缺少参数token"),
	REGIONTOKENERROR(47007,"token错误"),
	PARENTREGIONMISS(47008,"父级区域未设置"),
	STATIONSHIPPINGNOTFOUND(47009,"未找到分拨中心快递"),
	ORDETTYPENOTEXIST(47010,"订单类型不存在"),
	STATIONCANNOTUSE(47011,"该分拨中心不可用"),
	BUSINESSTYPENOTSET(47012,"业务类型未审核"),
	FACILITYWITHOUTAREA(47013,"大区与仓库不一致"),
	AREANOTSET(47014,"未设置大区"),
	EMAILNOTVERIFY(47015,"未验证邮箱"),
	EMAILNOTBIND(47016,"未绑定邮箱"),
	EMAILNOTFOUNT(47017,"未找到邮箱"),
	EMAILFORMERROR(47018,"邮箱格式错误"),
	EMAILNOTATCHECKINT(47019,"邮箱未处在待验证状态"),
	NOTINGROUP(47020,"不属于该系统"),
	EFFECTTIMEERROR(47021,"设置生效时间小于前生效时间"),
	SENDMAILFAIL(47022,"邮件发送失败"),
	TOKENOUTTIME(47023,"token已过期,请重新登录"),
	TOKENCREATEEX(47024,"token生成异常"),
	ILLEGALSIGN(99999,"请勿违法攻击或入侵本站点，此次访问已被记录备案"),
	ILLEGALOPERATE(47025,"非法操作"),
	TOKENUNEFFECT(47026,"token已失效, 请重新发送邮件"),
	USERDELETE(47027,"该账号已被冻结，请联系管理员"),
	EMAILCHECKING(47028,"用户邮箱未验证，请联系管理员重置密码"),
	PERMISSIONNOTFOUND(47029,"未找到权限"),
	PASSWORDSAME(47030,"新旧密码重复！"),
	USERNAEMERROR(47031,"用户名不能为中文"),
	USERNAMEEXIST(47032,"用户名已存在"),
	IPNOTLOGIN(47033,"该IP未允许登陆");

	
	
	
	
	
	
	private String text;
	private int value;
	
	private ErrorCodeEnum(int value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public int getValue() {
		return value;
	}

	public String getText() {
		return text;
	}
	
	public static String getText(int value) {
		for (ErrorCodeEnum typeEnum : ErrorCodeEnum.values()) {
			if (typeEnum.getValue() == value) {
				return typeEnum.getText();
			}
		}
		return null;
	}
	
	public static ErrorCodeEnum get(int value) {
		for (ErrorCodeEnum typeEnum : ErrorCodeEnum.values()) {
			if (typeEnum.getValue() == value) {
				return typeEnum;
			}
		}
		return null;
	}
}
