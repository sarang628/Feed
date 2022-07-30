git clone --recurse-submodules https://github.com/sarang628/FeedScreenTestApp.git


# 네비게이션
## NotLoggedInMenuBottomSheetNavigation
피드 메뉴 클릭 시 비로그인 상태라면 해당 내비게이션 이동

### settings.gradle 설정
include ':bagleBottomSheetDialog'
project(':bagleBottomSheetDialog').projectDir = new File("./Screens/BottomSheetDialog2/bagle_bottom_sheet_dialog")

### git 경로
