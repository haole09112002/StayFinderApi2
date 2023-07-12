# StayFinderApi2
API được viết trong học phần Lập trình di động cho một ứng dụng Androin về tìm kiếm phòng trọ.
## Các công nghệ sử dụng:
- Spring boot
- Spring Sercurity, JWT
- Spring Data JPA
- MySQL
## Mô tả chức năng
- Có 2 role: ROLE_USER và ROLE_ADMIN
- Guest có thể xem thống tin các phòng trọ đã được đăng
  + Tìm kiếm phòng trọ theo các tiêu chí giá cả điện, nước, số người ở, giá phòng, địa điểm
  + Xem các bình luận.
  + Xem thông tin người đăng bài
- ROLE_USER: Người có thể vừa là người 
  + Bao gồm các chức năng của Guest
  + Đăng thông tin về bài viết mới và đợi Admin phê duyệt.
  + Chỉnh sửa thông tin bài đăng
  + Bình luận
  + Thêm bài viết vào danh sách yêu thích
- ROLE_ADMIN:
  + Phê duyệt/ từ chối các bài đăng
  + Chặn các tài khoản
