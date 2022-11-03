# 线上部署
使用端口：8800
数据库名：hot_wall
JAR包名： csust_hot_wall-0.0.1-SNAPSHOT.jar
项目地址：/www/wwwroot/springboot/hot_wall
# 工程简介
这是一个与浩总开发的关于文章与视频的项目，这里作为后端接口存在，还没有写管理系统嘞

webflux:用于网络请求
@RestControllerAdvice
用来实现服务层向控制层返回提示信息，不在用简单的布尔判断或复杂的数字判断
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
        - [X] 自动获取用户ID作为作者信息
    - [x] 修改
        - [X] 无法修改作者、创建时间、点赞、浏览量
        - [X] 所有人只能修改自己的文章
    - [x] 删除
        - [X] 同时减少所在类别的文章数
        - [ ] 同时删除所有关于此文章的点赞
        - [X] 管理员和文章作者才能删除
    - [x] 查询全部与分页查询
    - [X] 通过Id查询
    - [X] 查询文章详情
        - [X] 同时增加文章浏览量
    - [X] 条件查询(可分页)
        - [X] 通过文章标题查询 key:name
        - [X] 通过文章ID查询 key:id
        - [X] 通过作者名查询 key:author
        - [X] 通过作者ID查询 key:uid
        - [X] 通过简介查询 key:intro
        - [X] 通过类别ID查询 key:cid
    - 逻辑
        - [X] 通用查询不查content内容字段，在Mapper中修改。只要查询文章详情可查到content内容
        - [X] 所有查询增加作者昵称、类别名、被收藏数、评论总数
- 类别
    - [X] 增加
        - [X] 名称不能相同
        - [X] 用户无权增加
    - [X] 修改
        - [X] 普通修改不能修改创建日期和文章数
        - [X] 用户无权修改
    - [X] 删除
        - [X] 存在文章内容时不能删除
        - [X] 用户无权删除
    - [X] 查询全部与分页查询
    - [X] 通过Id查询
- 用户
    - [X] 增加
        - [X] 用户无权增加
        - [X] openid不能相同
    - [X] 修改
        - [X] 普通修改不能修改openid、创建日期和更新日期
        - [X] 用户角色只能修改自己
    - [X] 删除
        - [X] 存在文章关系的用户不能删除
        - [X] 存在关注关系的用户不能删除
        - [X] 存在评论关系的用户不能删除
        - [X] 存在收藏的用户不能删除
        - [X] 同时删除点赞记录
        - [X] 用户角色无权删除
    - [X] 通过code登录
        - [X] 返回Token
    - [X] 查询全部与分页查询
    - [X] 通过Id查询
    - [X] 通过openid查询
    - [X] 条件查询(可分页)
        - [X] 昵称查询 key:nickname
    - 逻辑
        - [X] 所有查询增加文章数、收藏数、关注数、粉丝数、点赞数、评论数
- 收藏
    - [X] 增加
        - [X] 联合主键（用户编号，文章编号）不能相同
        - [X] 用户、文章ID必须有效
        - [X] 登录用户只能增加自己的收藏，无需传入用户id
    - [X] 不能修改
    - [X] 删除
        - [X] 登录用户只能删除自己的收藏
    - [X] 普通用户通过文章id删除自己的收藏
    - [X] 查询全部与分页查询
    - [X] 通过Id查询
    - [X] 通过用户ID查询
        - [X] 登录用户只能查询自己的收藏
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
    - [X] 查询全部与分页查询
    - [X] 条件查询(可分页)
        - [X] 通过用户id查询关注列表 key:uid
        - [X] 通过关注用户id查询粉丝列表 key:fid
    - [X] 通过用户id查询关注列表
    - [X] 通过关注用户id查询粉丝列表
    - [X] 通过用户id查询关注总数
    - [X] 通过关注id查询粉丝总数
    - 逻辑
        - [X] 所有查询关注信息增加关注者用户名、被关注者用户名
- 喜欢(点赞)
    - [X] 添加
        - [X] 不能点赞不存在的文章
        - [X] 点赞用户必须是存在的
        - [X] 同步增加文章属性中的点赞数
    - [X] 不能修改
    - [X] 删除(取消点赞)
        - [X] 同步减少文章属性中的点赞数
    - [X] 查询全部与分页查询
    - [X] 条件查询(可分页)
        - [X] 通过文章id查询点赞用户列表 key:aid
        - [X] 通过用户id查询点赞文章列表 key:uid
    - [X] 通过文章id查询点赞用户列表
    - [X] 通过用户id查询点赞文章列表
    - [X] 通过文章id查询点赞总数
    - [X] 通过用户id查询点赞文章总数
    - 逻辑
        - [X] 所有查询点赞信息增加用户名、点赞文章名、文章作者名
- 榜单
    - [X] 更新热榜
    - [X] 查询热榜
    - [X] 更新新榜
    - [X] 查询新榜
    - [X] 随机获取一条热榜
    - [X] 随机获取一条新榜
    - 逻辑
        - [X] 发布前4个小时不上热榜，上新榜
        - [X] 热榜每两小时更新，新榜每一小时更新
        

##### 热度算法
> 十个人浏览十个人评论与一百个人浏览十个人评论哪个更火一点
> 每两小时更新一次，发布时间小于四小时的不计算在榜单中
> 每日新榜一小时更新一次，统计今天发布的榜单信息，今天没有则显示未发布

使用 hacker news 的排名算法
Score=P/T^G

P = 浏览数 + 1 + (点赞 * 2 + 评论回复 * 4 + 收藏 * 8) ^ G 
T = ((发布间隔小时 + 1) - (发布间隔小时 - 最近评论间隔小时) / 2) (PS:间隔小时用当前时间减去发布或评论时间)
G = 1.1 (PS:G数越大，时间久的文章排名下滑越快)

10人10点赞10评论0收藏2发布间隔0最新评论间隔
10 + 1 + (20 + 40 + 0)^1.1 = 101.36
(3 - (3/2)) ^ 1.1 = 1.56
101.36 / 1.56 = 64.97

100人100点赞10评论2收藏24发布间隔0最新评论间隔
100 + 1 + (200 + 40 + 16)^1.1 = 546.72
(24 - 12) ^ 1.1 = 15.39
564.72 / 15.39 = 36.7