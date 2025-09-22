# Feed Module

## 피드 로딩, 오류, 성공
Feed 모듈에는 항목 UI가 없음.<br>
다른 화면들에서도 사용을 고려해 FeedItem을 제공하는 모듈이 따로 있음.<br>
피드 리스트에서 무한 스크롤, 끌어 당겨 새로고침, 로딩, 오류, 성공 상태 등을 제공.

## Feature
- 피드 항목을 리스트로 표시
- 스크롤 갱신, 스크롤 로딩 기능 제공

<img src="../screenshots/screen.png"/>



## package

## Architecture

### UI elements
[#### compose 명] (https://github.com/sarang628/TorangArchitecture?tab=readme-ov-file#compose-%EC%9E%91%EC%84%B1-%EB%B0%A9%EB%B2%95)

FeedScreen

### UI Layer

#### UI element

UI elements such as activities and fragments that display the data

#### UI state

The UI state is what the app says they should see.


#### State holder(ViewModel)

Responsible for the production of UI state and contain the necessary logic for that task

### Domain Layer

#### UseCase

|-|-|-|
| 즐겨찾기 추가 | AddFavoriteUseCase |
| 좋아요 추가 | AddLikeUseCase |
| 즐겨찾기 삭제 | DeleteFavoriteUseCase |
| 좋아요 삭제 | DeleteLikeUseCase |
| 피드 갱신 | FeedRefreshUseCase |
| 피드 페이징 | FeedWithPageUseCase |
| 식당 ID에 해당 모든 피드 | GetFeedByRestaurantIdFlowUseCase |
| 리뷰 ID의 사용자 모든 피드 | GetFeedByReviewIdUseCase |
| 피드 불러오기 | GetFeedFlowUseCase |
| 내 피드 가져오기 | GetMyFeedFlowUseCase |

## Illustrating cycle of event

<img src="../screenshots/event_cycle.jpg">

## UnitTest

like가 1개 이상이면 갯수를 표시 한다.
comment가 1개 이상이면 코멘트 갯수를 표시 한다.

## What was difficult

- 유닛코드 테스트 작성 어려움(현재 진행중)
    - 명제와 진리표 등을 작성해서 코드로 옮겨야 함.
    - 테스트 라이브러리 학습 필요.
- 피드 리스트 공통 모듈작업
    - 피드 리스트는 프로필 화면 음식점 상세화면 등에도 사용하여 공통 모듈로 작업 필요.
    - 공통 모듈의 라이브러리 의존성을 추가하지 않고 주입하는 방법을 사용
    - 의존성 없이 공통 모듈을 주입하는데 여러 추가 작업이 필요 했음.
    - 이게 맞는 방법인지 잘 모르겠음.

## Preview

<img src="../screenshots/preview.gif" />