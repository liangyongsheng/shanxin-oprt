sample
===
	select #use("cols")# from oprt where #use("condition")#
cols
===
	id,secret,loginName,loginPwd,name,flag,createTime,remark
condition
===
	1 = 1
	and flag = 1
	@if(!isEmpty(id)){
		and `id`=#id#
	@}
	@if(!isEmpty(loginName)){
		and `loginName`=#loginName#
	@}
