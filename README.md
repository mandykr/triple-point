# point-service

## Required
- Java 11
- Gradle 7.4.1
- Mysql 5.7 - using default port ( 3306 )
- Zookeeper - using default port( 2181 )
- Kafka - using port ( 9092 )

## Install
### docker & docker compose
   - https://docs.docker.com/desktop/
### git clone
```
git clone https://github.com/mandykr/triple-point.git
```

## How to use
1. docker(docker desktop) 실행
2. zookeeper, kafka, mysql 실행
   ```
   cd git-clone-path/docker-files
   docker-compose -f docker-compose.yml up -d
   ```
3. point-service 실행
   ```
   cd git-clone-path/point-service
   ./gradlew bootRun
   ```

## APIs
### 포인트 적립
- HTTP request
```
POST /events HTTP/1.1
Content-Type: application/json
Host: localhost:8080

{
    "attachedPhotoIds": [
        "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
        "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
    ],
    "placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f",
    "action": "ADD",
    "type": "REVIEW",
    "reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
    "userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
    "content": "좋아요!"
}
```

- Request fields

|Path|Type|Description|
|---|---|---|
|type|String|이벤트 유형|
|action|String|이벤트 액션 (ADD: 추가, MOD: 수정, DELETE: 삭제)|
|reviewId|String|리뷰 ID|
|content|String|리뷰 내용|
|attachedPhotoIds|Array|첨부 사진 ID 목록|
|userId|String|사용자 ID|
|placeId|String|장소 ID|

- HTTP response
```
HTTP/1.1 201 Created
Location: /events
Content-Type: application/json

[
    {
        "id": 1,
        "type": "WRITE_TEXT",
        "action": "ADD",
        "reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
        "userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
        "placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f",
        "point": 1
    },
    {
        "id": 2,
        "type": "ATTACHED_PHOTOS",
        "action": "ADD",
        "reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
        "userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
        "placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f",
        "point": 1
    }
]
```

- Response fields

|Path|Type|Description|
|---|---|---|
|id|Number|이벤트 ID|
|type|String|이벤트 유형 <br>(WRITE_TEXT: 텍스트, <br>ATTACHED_PHOTOS: 사진, <br>ADDED_FIRST_REVIEW_ON_PLACE: 장소)|
|action|String|이벤트 액션 (ADD: 추가, DELETE: 삭제)|
|reviewId|String|리뷰 ID|
|userId|String|사용자 ID|
|placeId|String|장소 ID|
|point|Number|포인트 점수|

<br/>

### 포인트 조회
- HTTP request
```
GET /points?userId=3ede0ef2-92b7-4817-a5f3-0c575361f745 HTTP/1.1
Content-Type: application/json
Host: localhost:8080
```

- Path parameters

|Path|Description|
|---|---|
|userId|사용자 ID|

- HTTP response
```
HTTP/1.1 200 OK
Location: /events
Content-Type: application/json

{
    "id": "f7356e73-adbc-4167-a703-7bec4dce9c70",
    "userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
    "point": 2
}
```

- Response fields

|Path|Type|Description|
|---|---|---|
|id|String|포인트 ID|
|userId|String|사용자 ID|
|point|Number|포인트 점수|
