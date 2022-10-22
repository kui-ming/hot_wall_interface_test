# 工程简介
# 长沙理工热点墙后台接口

表
文章
id、类别id、用户id、标题、简介、内容、创建时间、更新时间、浏览人数、点赞人数、图片列表、状态

用户
id、openid、昵称、头像地址、创建时间、更新时间、状态

类别
id、标题、文章数量、创建时间、更新时间、状态

评论
id、文章id、回复id、用户id、内容、创建时间、更新时间、状态

收藏
id、用户id、文章id、时间

关注
用户id、关注用户id、时间

点赞
用户id、文章id、时间

# 预想功能
- 文章
    - [X] 增加
        - [X] 同时增加所在类别的的文章数
        - [X] 标题名不能重复，无效类别、无效用户不能添加
    - [x] 修改
    - [x] 删除
        - [X] 同时减少所在类别的文章数
        - [ ] 管理员和文章作者才能删除
    - [x] 查询全部与分页查询.k
    - [X] 通过Id查询
    - [ ] 条件查询(可分页)
    - 逻辑
        - [X] 通用查询不查content内容字段，在Mapper中修改
        - [X] 所有查询增加作者昵称、类别名
- 类别
    - [X] 增加
        - [X] 名称不能相同
    - [X] 修改
        - [X] 普通修改不能修改创建日期和文章数
    - [X] 删除
        - [X] 存在文章内容时不能删除
    - [X] 查询全部与分页查询
    - [X] 通过Id查询
    - [ ] 条件查询(可分页)
    - 逻辑
        - [ ] 日期自动改变BUG，当增加与减少文章数字段时更新时间会改变，但修改类别名同样会改变
- 用户
    - [X] 增加
        - [X] openid不能相同
    - [X] 修改
        - [X] 普通修改不能修改创建日期和更新日期
    - [X] 删除
        - [X] 存在文章关系的用户不能删除
        - [ ] 存在关注关系的用户不能删除
        - [ ] 存在评论关系的用户不能删除
        - [ ] 存在收藏的用户不能删除
        - [ ] 同时删除点赞记录
    - [X] 查询全部与分页查询
    - [X] 通过Id查询
    - [X] 通过openid查询
    - [ ] 条件查询(可分页)
    - [ ] 查询收藏的文章
    - 逻辑
        - [ ] 所有查询增加文章数、收藏数、点赞数、评论数
- 收藏
    - [X] 增加
        - [X] 联合主键（用户编号，文章编号）不能相同
        - [X] 用户、文章ID必须有效
    - [X] 不能修改
    - [X] 删除
    - [X] 查询全部与分页查询
    - [X] 通过Id查询
    - [X] 通过用户ID查询
    - [X] 通过文章ID查询
    - [X] 条件查询(可分页)
        - [X] 通过用户id查询根据时间倒序 key:uid
        - [X] 通过文章id查询根据时间倒序 key:aid
- 评论
    - [X] 增加
        - [X] 评论用户和文章必须有效
        - [X] 父评论id存在时判断父评论必须是有效评论
        - [X] 父评论中的文章id与此评论的文章id必须相同
    - [X] 修改
        - [X] 不能修改时间、父评论、评论用户和文章
        - [ ] 评论用户和管理员才能修改
    - [X] 删除
        - [ ] 父评论删除时回复评论不用随之删除，但在查询回复评论时会显示父评论已删除
        - [ ] 评论用户和管理员才能删除
    - [X] 查询全部与分页查询
    - [X] 通过id查询
    - [X] 条件查询(可分页)
        - [X] 通过评论用户Id查询评论 key:uid
        - [X] 通过文章Id查询评论 key:aid
        - [X] 通过父评论Id查询评论 key:rid
    - [X] 通过评论用户Id查询评论
    - [X] 通过文章Id查询评论
    - [X] 通过父评论Id查询回复
    - 逻辑
        - [X] 所有查询评论信息增加评论用户名、文章名、文章作者、文章简介、回复用户名、回复评论信息
- 关注
    - [X] 添加
        - [X] 不能关注不存在的用户
        - [X] 用户必须是存在的
    - [X] 不能修改
    - [X] 删除
    
- 喜欢
##### @RestControllerAdvice
用来实现服务层向控制层返回提示信息，不在用简单的布尔判断或复杂的数字判断