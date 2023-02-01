# EmployeeManager
Ngôn ngữ lập trình: Java

Công nghệ sử dụng:

Back-end:
 - Spring Boot
 - Spring Data JPA
 - Spring Security
 - Spring Dev Tools
 - Thymeleaf
 - Maven
 
Database:
 - SQL
 - MySQL
 
Front-end:
 - HTML/CSS
 - Bootstrap
 
 Tính năng chính:
 
  - CRUD
  - Phân quyền , xác thực người dùng
  - Đăng kí , đăng nhập , đăng xuất
  - Remember me (không cần đăng nhập vào những lần tiếp theo)
  - Quên mật khẩu (giúp đặt lại mật khẩu thông qua đường link được gửi đến email của người dùng)
  - Phân trang
  - Upload ảnh
  - Chức năng tìm kiếm giúp người dùng tìm kiếm thông tin
  
 Demo chương trình:
 
 - Truy cập vào đường dẫn: http://localhost:8080
 
 - Ấn vào đường link Employee Manager
 
 - Phân quyền và xác thực:
 
    - Tài khoản ADMIN:
       + E-mail: levieth710@gmail.com
       + Password: hoang1309
    
       Chức năng của tài khoản ADMIN: Sửa và xóa thông tin người dùng
    - Tài khoản USER:
 
       Nếu người dùng chưa có tài khoản , phải đăng kí tài khoản
   
       Chức năng của tài khoản USER: Chỉ được xem thông tin , không có quyền sửa hoặc xóa thông tin người dùng
  
  - Người dùng có thể dùng chức năng Remember me để không cần đăng nhập lại vào những lần tiếp theo
  
  - Nếu người dùng quên mật khẩu, dùng chức năng Forgot Password. Một đường link sẽ được gửi đến email người dùng để giúp đặt lại mật khẩu
  
  - Khi đã đăng nhập thành công, người dùng có thể dùng chức năng tìm kiếm để giúp tìm thông tin của mình nhanh chóng
  
  - Chức năng phân trang giúp quản lí nội dung thông tin của người dùng thành những trang nhỏ tăng trải nghiệm người dùng
  
  - Nếu người dùng muốn đăng xuất khỏi chương trình, dùng chức năng Logout
  
  
 
 
