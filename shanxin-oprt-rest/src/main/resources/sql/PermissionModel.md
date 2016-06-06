sample
===
	select #use("cols")# from permission where #use("condition")#
cols
===
	id,code,name,needAccessToken,needCheck,createTime,remark
condition
===
	1 = 1
	@if(!isEmpty(id)){
		and `id`=#id#
	@}
	@if(!isEmpty(code)){
		and `code`=#code#
	@}

oprtPermission
===
*-----------------------------------------------

	select 
		DISTINCT #use("cols")# 
	from 
		permission
	where 
		(
			id in (select permissionId from oprt_permission_owership where oprtId = #oprtId#)
				or 
			id in (select permissionId from permission_group_permission_owership 
						where permissionGroupId in( select permissionGroupId from oprt_permission_group_owership where oprtId = #oprtId#)
					)
		)
		@if(!isEmpty(code)){
	 		and `code`=#code#
		@}
