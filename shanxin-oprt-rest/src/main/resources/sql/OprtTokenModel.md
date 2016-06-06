sample
===
	select #use("cols")# from oprt_token where #use("condition")#
cols
===
	id,oprtId,oprtSecret,accessToken,expiredDate,refreshToken,createTime,lastUpdateTime,remark
condition
===
	1 = 1
	@if(!isEmpty(id)){
		and `id`=#id#
	@}
	@if(!isEmpty(oprtId)){
		and `oprtId`=#oprtId#
	@}
	@if(!isEmpty(oprtSecret)){
		and `oprtSecret`=#oprtSecret#
	@}
