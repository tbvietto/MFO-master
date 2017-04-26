# MFO
@Yêu cầu:
- Java version > 1.6
- Eclipse

@Các file trong code:
1. require.txt: 
  file này có định dạng: N fileName1 fileName2 ... fileNameN
  với N là số file tsp sẽ đọc, fileName1...N là tên các file tsp truyền vào, các file tsp này phải để ngang hàng với thư mục /src
2. City.java:
  file này lưu tọa độ các thành phố
3. TSP.java
  Mỗi đối tượng của class này sẽ lưu thông tin của 1 bài toán tsp truyền vào, bao gồm số thành phố 
  và ma trận khoảng cách giữa các thành phố với nhau.
4. Individual.java
  Mỗi đối tượng của class này sẽ là 1 lời giải trong bài toán tổng quát, class này có 1 số hàm và thuộc tính chính sau:
  - init(): khởi tạo
  - readFileRequire(): hàm đọc file require.txt, trả về giá trị là số lượng bài toán tsp muốn giải
  - readFileDistances(): hàm đọc từng file.tsp, trả về số lượng thành phố của mỗi file
  - saveDistances(): Hàm đọc tọa độ cụ thể của từng thành phố, tính toán khoảng cách giữa chúng lưu vào ma trận với mỗi đối tượng tsp tương ứng
  - get/setFitness(): các hàm tác động vào arraylist fitness
  - decode: hàm giải mã từ không gian chung về không gian riêng
  - compareTo(): hàm so sánh 2 cá thể dựa vào fitness.get(0)
  - compareByScalarFitness: hàm so sánh 2 cá thể dựa vào scalarFitness. Hàm này và hàm compareTo ở trên sẽ phục vụ cho việc sắp xếp quần thể 
  - mutation(): đột biến
 5. Population.java
  File này sẽ khởi tạo 1 quần thể có N (ở đây là 100) cá thể, 1 số hàm chính:
  - calculateScalarFitness(): Hàm này sẽ tính scalarFitness cho mỗi cá thể trong quần thể và gắn luôn skillFactor cho chúng
  - pMX(): lai ghép như GA, không có gì đặc biệt
  - crossOver(): thực hiện chọn ngẫu nhiên 2 cá thể, lai ghép nếu cùng skillFactor, đột biến nếu không
 6. Algorithm.java: file chứa hàm main để thực thi của chương trình
  - Trong file này có chứa hàm run() chính là hàm chạy, trong hàm này có chứa các bước lựa chọn, lai ghép/đột biến, tạo quần thể tạm gồm 200 phần tử, sắp xếp quần thể tạm theo scalarFitness, chọn 100 phần tử có scalarFiness tốt nhất để đưa vào quần thể mới.
  - trong hàm main(), chỉ việc gọi hàm run() bao nhiêu lần tùy ý muốn.
  
@Cách chạy: 
 - Thay đổi nội dung file require.txt để chạy với số lượng và các bài toán tsp khác nhau.
 - Thường thì nên để vòng for trong hàm main() của file Algorithm.java chạy khoảng 300-500 lần là đã hội tụ rồi. 
