package com.example.goldenticketnew;

//import com.example.goldenticketnew.config.ConnectCadance;
import com.example.goldenticketnew.config.cadance.CadanceWorkFlow;
import com.uber.cadence.client.WorkflowClient;
import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.internal.compatibility.Thrift2ProtoAdapter;
import com.uber.cadence.internal.compatibility.proto.serviceclient.IGrpcServiceStubs;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class GoldenTicketNewApplication {


	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	static final String TASK_LIST = "HelloActivity";

	public static void main(String[] args) {
		SpringApplication.run(GoldenTicketNewApplication.class, args);
		// Get a new client
		// NOTE: to set a different options, you can do like this:
		// ClientOptions.newBuilder().setRpcTimeout(5 * 1000).build();
		WorkflowClient workflowClient =
			WorkflowClient.newInstance(
				new Thrift2ProtoAdapter(IGrpcServiceStubs.newInstance()),
				WorkflowClientOptions.newBuilder().setDomain("golden-new").build());
		// Get worker to poll the task list.
		WorkerFactory factory = WorkerFactory.newInstance(workflowClient);
		Worker worker = factory.newWorker(TASK_LIST);
		// Workflows are stateful. So you need a type to create instances.
		worker.registerWorkflowImplementationTypes(CadanceWorkFlow.BookWorkflowImpl.class);
		// Activities are stateless and thread safe. So a shared instance is used.
		worker.registerActivitiesImplementations(new CadanceWorkFlow.BookingActivitiesImpl());
		// Start listening to the workflow and activity task lists.
		factory.start();
		boolean test = true;
		// Get a workflow stub using the same task list the worker uses.
		CadanceWorkFlow.BookWorkflow workflow = workflowClient.newWorkflowStub(CadanceWorkFlow.BookWorkflow.class);
		// Execute a workflow waiting for it to complete.
		System.out.println("Dat ve thanh cong vui long doi 20s lay ten phim");
		String greeting
			= workflow.getBooking("haha");
		System.out.println(greeting);

		System.exit(0);
	}

//
//    @Autowired
//    private UserRepository userRepository;
//

//
//    @Autowired
//    private IBranchRepository branchRepository;
//
//    @Autowired
//    private IRoomRepository roomRepository;
//
//    @Autowired
//    private IScheduleRepository scheduleRepository;
//
//    @Autowired
//    private ISeatRepository seatRepository;
//
////     Do ch??a c?? trang admin ????? th??m phim v?? l???ch chi???u n??n th??m t???m d??? li???u xu???ng db ????? demo
//    @PostConstruct
//    public void init() {
//         Ch???y 1 l???n ?????u app r???i b??? comment ??o???n n??y r???i ch???y l???i ????? add data gh??? ng???i cho ph??ng 1
//        Room room = roomRepository.findById(1).get();
//
//        for(int i=1;i<=8;i++){
//            Seat seat = new Seat();
//            seat.setName("A"+i);
//            seat.setRoom(room);
//            seatRepository.save(seat);
//        }
//
//        for(int i=1;i<=8;i++){
//            Seat seat = new Seat();
//            seat.setName("B"+i);
//            seat.setRoom(room);
//            seatRepository.save(seat);
//        }
//        for(int i=1;i<=8;i++){
//            Seat seat = new Seat();
//            seat.setName("C"+i);
//            seat.setRoom(room);
//            seatRepository.save(seat);
//        }
//        for(int i=1;i<=8;i++){
//            Seat seat = new Seat();
//            seat.setName("D"+i);
//            seat.setRoom(room);
//            seatRepository.save(seat);
//        }
//        for(int i=1;i<=8;i++){
//            Seat seat = new Seat();
//            seat.setName("E"+i);
//            seat.setRoom(room);
//            seatRepository.save(seat);
//        }
//
//
//
//            User admin = new User();
//            Set<Role> roles = new HashSet<>();
//            Role roleAdmin = new Role();
//
//            admin.setName("admin");
//            admin.setUsername("admin");
//            admin.setEmail("admin@gmail.com");
//            admin.setPassword("123456789");
//            admin.setRoles(roles);
//            userRepository.save(admin);


//        List<Movie> movies = movieRepository.findAll();
//        if (movies.isEmpty()) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//            Movie nhocTrum = addNewMovie("Nh??c Tr??m: N???i Nghi???p Gia ????nh","https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_boss_baby_2_24.12.2021_1_1_1__1.jpg",
//                    "Nh??c tr??m Ted gi??? ????y ???? tr??? th??nh m???t tri???u ph?? n???i ti???ng trong khi Tim l???i c?? m???t cu???c s???ng ????n gi???n b??n v??? anh Carol v?? hai c?? con g??i nh??? y??u d???u. M???i m??a Gi??ng sinh t???i, c??? Tina v?? Tabitha ?????u mong ???????c g???p ch?? Ted",
//                    "Nh??c tr??m Ted gi??? ????y ???? tr??? th??nh m???t tri???u ph?? n???i ti???ng trong khi Tim l???i c?? m???t cu???c s???ng ????n gi???n b??n v??? anh Carol v?? hai c?? con g??i nh??? y??u d???u. M???i m??a Gi??ng sinh t???i, c??? Tina v?? Tabitha ?????u mong ???????c g???p ch?? Ted nh??ng d?????ng nh?? hai anh em nh?? Templeton nay ???? kh??ng c??n g???n g??i nh?? x??a. Nh??ng b???t ng??? thay khi Ted l???i c?? m??n t??i xu???t kh??ng th??? ho??nh tr??ng h??n khi ????p th???ng m??y bay tr???c th??ng t???i nh?? Tim tr?????c s??? ng??? ng??ng c???a c??? gia ????nh.",
//                    "https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/r/s/rsz_dr-strange-980x448.jpg",
//                    "Tom McGrath","Amy Sedaris, Jeff Goldblum, James Marsden",
//                    "Ho???t H??nh",LocalDate.parse("24/12/2021",formatter),
//                    105,"https://www.youtube.com/embed/Lv8nL2q8yRI",
//                    "Ti???ng Anh v???i ph??? ????? ti???ng Vi???t v?? l???ng ti???ng Vi???t",
//                    "P - PHIM D??NH CHO M???I ?????I T?????NG",1);
//            Movie venom = addNewMovie("Venom: ?????i M???t T??? Th??","https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_venom_121121_1__1.jpg",
//                    "Sie??u bom ta????n #VENOM: LET THERE BE CARNAGE hu????a he??n tra????n chie????n kho????c lie????t nha????t giu????a Venom va?? ke?? thu?? truye????n kie????p, Carnage.",
//                    "Sie??u bom ta????n #VENOM: LET THERE BE CARNAGE hu????a he??n tra????n chie????n kho????c lie????t nha????t giu????a Venom va?? ke?? thu?? truye????n kie????p, Carnage.",
//                    "https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/b/l/blackpink-rolling_1_.jpg",
//                    "Andy Serkis","Tom Hardy, Michelle Williams, Woody Harrelson, Naomie Harris",
//                    "H??nh ?????ng, Khoa H???c Vi???n T?????ng, Phi??u L??u, Th???n tho???i",
//                    LocalDate.parse("10/12/2021",formatter),97,"https://www.youtube.com/embed/EVWdzVtSh1I",
//                    "Ti???ng Anh - Ph??? ????? Ti???ng Vi???t","C13 - PHIM C???M KH??N GI??? D?????I 13 TU???I",1);
//            Movie maTran = addNewMovie("Ma Tr???n: H???i Sinh","https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_matrix_4_1__1.jpg",
//                    "Sau 20 n??m, si??u ph???m ma tr???n ???? tr??? l???i v???i ng?????i xem, Neo is back! Li???u ????y c?? ph???i ph???n k???t cho franchise n??y",
//                    "Ma Tr???n: H???i Sinh l?? ph???n phim ti???p theo r???t ???????c tr??ng ?????i c???a lo???t phim ???Ma Tr???n??? ????nh ????m, ???? g??p ph???n t??i ?????nh ngh??a th??? lo???i phim khoa h???c vi???n t?????ng. Ph???n phim m???i nh???t n??y ????n ch??o s??? tr??? l???i c???a c???p ????i Keanu Reeves v?? Carrie-Anne Moss v???i vai di???n bi???u t?????ng ???? l??m n??n t??n tu???i c???a h???, Neo v?? Trinity. Ngo??i ra, phim c??n c?? s??? g??p m???t c???a d??n di???n vi??n ?????y t??i n??ng g???m Yahya Abdul-Mateen II, Jessica Henwick, Jonathan Groff, Neil Patrick Harris, Priyanka Chopra Jonas v?? Christina Ricci.",
//                    "https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/b/l/blackpink-rolling_1_.jpg",
//                    "Lana Wachowski","Keanu Reeves, Carrie-Anne Moss, Yahya Abdul-Mateen II, Jessica Henwick, Jonathan Groff, Neil Patrick Harris, Priyanka Chopra Jonas v?? Christina Ricci",
//                    "H??nh ?????ng, Khoa H???c Vi???n T?????ng",LocalDate.parse("24/12/2021",formatter),
//                    148,"https://www.youtube.com/embed/l2UTOJC5Tbk",
//                    "Ti???ng Anh - Ph??? ????? Ti???ng Vi???t, Ph??? ????? Ti???ng H??n",
//                    "C18 - PHIM C???M KH??N GI??? D?????I 18 TU???I",1);
//            Movie doremon = addNewMovie("Doraemon: ??i B???n ??i 2","https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_doremon_2_1__1.jpg",
//                    "M???t ng??y n???, Nobita v?? t??nh t??m th???y ch?? g???u b??ng c??, m??n ????? ch??i ch???t ch???a bao k??? ni???m c??ng ng?????i b?? ????ng k??nh. V???i kh??t khao ???mu???n g???p b?? l???n n???a???, Nobita ???? tr??? v??? qu?? kh??? b???ng c??? m??y th???i gian, b???t ch???p s??? ph???n ?????i c???a Doraemon",
//                    "M???t ng??y n???, Nobita v?? t??nh t??m th???y ch?? g???u b??ng c??, m??n ????? ch??i ch???t ch???a bao k??? ni???m c??ng ng?????i b?? ????ng k??nh. V???i kh??t khao ???mu???n g???p b?? l???n n???a???, Nobita ???? tr??? v??? qu?? kh??? b???ng c??? m??y th???i gian, b???t ch???p s??? ph???n ?????i c???a Doraemon. D?? ng???c nhi??n, b?? v???n tin c???u thi???u ni??n l???n t?????ng tr?????c m???t m??nh l?? ch??u m??nh. Tr?????c nguy???n v???ng tha thi???t ???mong ???????c th???y ch??u d??u m???t l???n???, chuy???n phi??u l??u c???a Doraemon v?? Nobita b???t ?????u. Nobita mu???n cho b?? xem ????m c?????i c???a m??nh, nh??ng ????ng ng??y th??nh h??n v???i Shizuka, ch?? r??? Nobita l???i tr???n m???t? Jaian v?? Suneo ch???y ????n ch???y ????o t??m b???n, c??n Shizuka v???n tin t?????ng ch??? ?????i Nobita. ????? th???c hi???n nguy???n v???ng c???a b??, ????p l???i ni???m tin c???a gia ????nh, b???n b?? v?? Shizuka y??u qu??, Nobita s??? c??ng Doraemon du h??nh v?????t th???i gian. H??? s??? mang ?????n cho ch??ng ta m???t c??u chuy???n c???m ?????ng ?????n r??i l??? v??? quan h??? con ng?????i, k???t n???i gi???a qu?? kh???, hi???n t???i v?? t????ng lai.",
//                    "https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/d/o/doreamon.jpg","Ryuichi Yagi, Takashi Yamazaki",
//                    "Wasabi Mizuta, Megumi Oohara, Yumi Kakazu, Subaru Kimura, Tomokazu Seki",
//                    "H??i, Ho???t H??nh",LocalDate.parse("17/12/2021",formatter),
//                    96,"https://www.youtube.com/embed/GXnOs4Hj8MA","Ti???ng Nh???t - Ph??? ????? Ti???ng Vi???t; L???ng ti???ng",
//                    "P - PHIM D??NH CHO M???I ?????I T?????NG",1);
//            Movie cauChuyenPhiaTay = addNewMovie("C??u Chuy???n Ph??a T??y","https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_wss_1200x1800__1.jpg",
//                    "???C??u chuy???n ph??a T??y??? k??? l???i c??u chuy???n t??nh y??u kinh ??i???n c???a Tony v?? Maria, gi???a s??? gi???ng x?? c???a t??nh y??u tr??? tu???i v?? s??? ng??n c???m, th?? h???n ??? th??nh ph??? NewYork nh???ng n??m 1950",
//                    "???????c ?????o di???n b???i ?????o di???n g???o c???i t???ng gi??nh gi???i Oscar Steven Spielberg, c??ng k???ch b???n b???i bi??n k???ch t???ng gi??nh gi???i Pulitzer Prize v?? gi???i Tony Award, Tony Kushner, ???C??u chuy???n ph??a T??y??? k??? l???i c??u chuy???n t??nh y??u kinh ??i???n c???a Tony v?? Maria, gi???a s??? gi???ng x?? c???a t??nh y??u tr??? tu???i v?? s??? ng??n c???m, th?? h???n ??? th??nh ph??? NewYork nh???ng n??m 1950.",
//                    "https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/w/s/wss_sneak_980x448.jpg",
//                    "Steven Spielberg","Ansel Elgort, Rachel Zegler, Ariana DeBose, David Alvarez, Mike Faist, Josh Andr??s Rivera, Ana Isabelle, Corey Stoll, Brian d???Arcy James, Rita Moreno",
//                    "Nh???c k???ch, T??nh c???m",LocalDate.parse("24/12/2021",formatter),
//                    156,"https://www.youtube.com/embed/QPvqV71P0Fo","Ti???ng Anh - Ph??? ????? Ti???ng Vi???t",
//                    "C16 - PHIM C???M KH??N GI??? D?????I 16 TU???I",1);
//            Movie blackPink = addNewMovie("BlackPink The Movie","https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/p/o/poster_blackpink_vie_2_1__1.jpg",
//                    "Nh??m nh???c n??? ???????c y??u th??ch to??n c???u, BLACKPINK s??? k??? ni???m n??m th??? 5 ho???t ?????ng c???a nh??m v???i vi???c ph??t h??nh BLACKPINK THE MOVIE",
//                    "Nh??m nh???c n??? ???????c y??u th??ch to??n c???u, BLACKPINK s??? k??? ni???m n??m th??? 5 ho???t ?????ng c???a nh??m v???i vi???c ph??t h??nh BLACKPINK THE MOVIE, ????y c??ng nh?? l?? m??n qu?? ?????c bi???t d??nh t???ng cho c??c BLINK??? fandom c???a BLACKPINK ??? b??? phim s??? t??i hi???n m???t c??ch s???ng ?????ng nh???ng k??? ni???m kh??ng th??? qu??n c??ng nh???ng m??n trinh di???n ?????y cu???ng nhi???t ????ng tinh th???n l??? h???i.",
//                    "https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/b/l/blackpink-rolling_1_.jpg",
//                    "Su Yee Jung, Oh Yoon-Dong","JISOO, JENNIE, ROS??, LISA",
//                    "Phim t??i li???u",LocalDate.parse("24/12/2021",formatter),99,
//                    "https://www.youtube.com/embed/Q_rK9UlUN-Q","Ti???ng H??n - Ph??? ????? ti???ng Vi???t",
//                    "P - PHIM D??NH CHO M???I ?????I T?????NG",1);
//            Movie nguoiNhen = addNewMovie("Ng?????i Nh???n: Kh??ng C??n Nh??","https://www.cgv.vn/media/catalog/product/cache/1/small_image/240x388/dd828b13b1cb77667d034d5f59a82eb6/s/n/snwh_poster_bluemontage_4x5fb_1__1.jpg"
//                    ,"??a v?? tr??? ???????c m??? ra, nh???ng k??? ph???n di???n n??o s??? tr???m ch??n spidey, c??ng ????n xem nh??",
//                    "L???n ?????u ti??n trong l???ch s??? ??i???n ???nh c???a Ng?????i Nh???n, th??n ph???n ng?????i h??ng x??m th??n thi???n b??? l???t m???, khi???n tr??ch nhi???m l??m m???t Si??u Anh H??ng xung ?????t v???i cu???c s???ng b??nh th?????ng v?? ?????t ng?????i anh quan t??m nh???t v??o t??nh th??? nguy hi???m. Khi anh nh??? ?????n gi??p ????? c???a Doctor Strange ????? kh??i ph???c l???i b?? m???t, ph??p thu???t ???? g??y ra l??? h???ng th???i kh??ng, gi???i ph??ng nh???ng ??c nh??n m???nh m??? nh???t t???ng ?????i ?????u v???i Ng?????i Nh???n t??? m???i v?? tr???. B??y gi???, Peter s??? ph???i v?????t qua th??? th??ch l???n nh???t c???a m??nh, n?? s??? thay ?????i kh??ng ch??? t????ng lai c???a ch??nh anh m?? c??n l?? t????ng lai c???a c??? ??a V?? Tr???."
//                    ,"https://www.cgv.vn/media/banner/cache/1/b58515f018eb873dafa430b6f9ae0c1e/r/s/rsz_dr-strange-980x448.jpg",
//                    "Jon Watts","Tom Holland, Zendaya, Benedict Cumberbatch, Jacob Batalon, Jon Favreau","H??nh ?????ng, Phi??u L??u",
//                    LocalDate.parse("17/12/2021",formatter),
//                    149,"https://www.youtube.com/embed/daHCu_jU5mQ",
//                    "Ti???ng Anh - Ph??? ????? Ti???ng Vi???t",
//                    "C13 - PHIM C???M KH??N GI??? D?????I 13 TU???I",1);
//
//            // T???o m???i c??c chi nh??nh
//            List<Branch> listBranch = branchRepository.findAll();
//            if(listBranch.isEmpty()){
//                Branch branch1 = new Branch();
//                branch1.setName("GOLDENNEW H?? ????ng");
//                branch1.setAddress("T???ng 4, M?? Linh Plaza H?? ????ng, ??. T?? Hi???u, P, H?? ????ng, H?? N???i");
//                branch1.setPhoneNo("0938473829");
//                branch1.setImgURL("https://www.google.com/maps/vt/data=01jbed2RV46dgYPWYrkUjQ6y9E_UiFnVBsCgIdJWh4TGqJw3K1Qg_A4phqg094CBuRXesGa3QOof0UPRtY3zUWjOKScSn-0JuYoAlbcSKeYWV9wooMdNPaY7iL3Yd9PJjxicmzPKGds-zZRAZ9hqPRix1Trxq2vTQ7GZDWXjNJrqjn2tqL8zO0gSrZgSmarAH0jaD9Ux5tVTaZCwchq2_nNCrs2vjOU7FeXQsPRqfM3jgoYPRe43jeLkd4KGtweeXwUPgV2AeBFj9yTmjBgHSswDBrmGoJkjk-0uRIIO6LCZyMAsSW1p7-gLsUI5nJF6zWCNKmesZ3Jd3I-17zEAOz3AmLMuLkRgiFkICakIuG9B0DkjzVK2P4jN203i4DNkXTpoxKHTMv9dZG-ZoW1A9w7Q5rzSukE8Zdt3GMMei-w-THF-qL3znIiK3nQKq1_BRtnFTvhXduCYpHCo3ZvIiBz4WNKjovXd9ppG-OlRtFLYh8kYvlCKWkO0bmkBeQXoT4mqHQXm80zs_P2CB4xE3bbtoUPgaNo2-5eUcO1CPh6n3DKUdkgOIjRflGoWijmrhJO_45gguPAqZ7ZcXmY_isBf4PnWWJnzO2VAHVQwqwYIJ503CVbm3bmWoX3nVyqThCPj3fFsvjxCH-uYT0VXi3IFc02ktKuirKrsSo2rcgTcTqto0LlmkPAm34wOAr2KmMCfiqJrjKKMCn62v7WefBbU3VLI7Z8pLIgrG4l258FsyN7unVKWcVl3TVnBWp-acw9Y9AmM-nu8OzD7HSpMjJM3X28MJGj9LIIC1WsEdVL0Jhq8x9vBkY9F0RT_XTLQxbIJYa3v0B9x6KlkdOlWqTQTHvc5HQz8OV0JQYp5roWX5WcIx06_gkNOvisnf-J3aeMgzGVGs_-dZUXPwNseutiOPQXyy5NfzhZuJDOAmCJLXEAhmB6BFzFMbATI5zQ9v-D2lDvsjYq2U3Mt7Ctp6HlZVb4DGhzu7FYZkBQ11KkbQthBNKrZQ3kTiVQNNf13Osr9fIK4W6m6R3FtkxqnPshtlc-PYArGqZimsKnxgxxwt1spelowhnI55qFR9wa6UdJgeyGGRfVyfDpFiRKUkwuB7Vip?h=505&w=561&scale=1");
//                branch1 = branchRepository.save(branch1);
//                Room room1 = new Room();
//                room1.setName("Ph??ng 101");
//                room1.setBranch(branch1);
//                room1.setCapacity(40);
//                room1.setTotalArea(80);
//                room1.setImgURL("http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg");
//                Room r1 = roomRepository.save(room1);
//                Schedule schedule1 = new Schedule();
//                schedule1.setBranch(branch1);
//                schedule1.setMovie(nguoiNhen);
//                schedule1.setRoom(r1);
//                schedule1.setStartDate(LocalDate.parse("2021-01-05"));
//                schedule1.setStartTime(LocalTime.parse("10:15"));
//                schedule1.setPrice(70000);
//                scheduleRepository.save(schedule1);
//
//                Schedule schedule5 = new Schedule();
//                schedule5.setBranch(branch1);
//                schedule5.setMovie(nguoiNhen);
//                schedule5.setRoom(r1);
//                schedule5.setStartDate(LocalDate.parse("2021-01-05"));
//                schedule5.setStartTime(LocalTime.parse("13:05"));
//                schedule5.setPrice(70000);
//                scheduleRepository.save(schedule5);
//
//                Schedule schedule6 = new Schedule();
//                schedule6.setBranch(branch1);
//                schedule6.setMovie(nguoiNhen);
//                schedule6.setRoom(r1);
//                schedule6.setStartDate(LocalDate.parse("2021-01-05"));
//                schedule6.setStartTime(LocalTime.parse("14:05"));
//                schedule6.setPrice(70000);
//                scheduleRepository.save(schedule6);
//
//                Schedule schedule7 = new Schedule();
//                schedule7.setBranch(branch1);
//                schedule7.setMovie(nguoiNhen);
//                schedule7.setRoom(r1);
//                schedule7.setStartDate(LocalDate.parse("2021-01-05"));
//                schedule7.setStartTime(LocalTime.parse("16:20"));
//                schedule7.setPrice(70000);
//                scheduleRepository.save(schedule7);
//
//
//                Schedule schedule8 = new Schedule();
//                schedule8.setBranch(branch1);
//                schedule8.setMovie(nguoiNhen);
//                schedule8.setRoom(r1);
//                schedule8.setStartDate(LocalDate.parse("2021-01-05"));
//                schedule8.setStartTime(LocalTime.parse("16:20"));
//                schedule8.setPrice(70000);
//                scheduleRepository.save(schedule8);
//
//                Schedule schedule9 = new Schedule();
//                schedule9.setBranch(branch1);
//                schedule9.setMovie(nguoiNhen);
//                schedule9.setRoom(r1);
//                schedule9.setStartDate(LocalDate.parse("2021-01-06"));
//                schedule9.setStartTime(LocalTime.parse("16:20"));
//                schedule9.setPrice(70000);
//                scheduleRepository.save(schedule9);
//
//                Schedule schedule10 = new Schedule();
//                schedule10.setBranch(branch1);
//                schedule10.setMovie(nguoiNhen);
//                schedule10.setRoom(r1);
//                schedule10.setStartDate(LocalDate.parse("2021-01-06"));
//                schedule10.setStartTime(LocalTime.parse("19:20"));
//                schedule10.setPrice(70000);
//                scheduleRepository.save(schedule10);
//
//                Schedule schedule = new Schedule();
//                schedule.setBranch(branch1);
//                schedule.setMovie(blackPink);
//                schedule.setRoom(r1);
//                schedule.setStartDate(LocalDate.parse("2021-01-05"));
//                schedule.setStartTime(LocalTime.parse("19:15"));
//                schedule.setPrice(70000);
//                scheduleRepository.save(schedule);
//
//                Room room2 = new Room();
//                room2.setName("Ph??ng 202");
//                room2.setBranch(branch1);
//                room2.setCapacity(40);
//                room2.setTotalArea(80);
//                room2.setImgURL("http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg");
//                Room r2 = roomRepository.save(room2);
//                Schedule schedule2 = new Schedule();
//                schedule2.setBranch(branch1);
//                schedule2.setMovie(blackPink);
//                schedule2.setRoom(r2);
//                schedule2.setStartDate(LocalDate.parse("2021-01-05"));
//                schedule2.setStartTime(LocalTime.parse("10:15"));
//                schedule2.setPrice(70000);
//                scheduleRepository.save(schedule2);
//
//                Schedule schedule77 = new Schedule();
//                schedule77.setBranch(branch1);
//                schedule77.setMovie(nguoiNhen);
//                schedule77.setRoom(r2);
//                schedule77.setStartDate(LocalDate.parse("2021-01-05"));
//                schedule77.setStartTime(LocalTime.parse("16:20"));
//                schedule77.setPrice(70000);
//                scheduleRepository.save(schedule77);
//
//
//                Room room3 = new Room();
//                room3.setName("Ph??ng 303");
//                room3.setBranch(branch1);
//                room3.setCapacity(40);
//                room3.setTotalArea(80);
//                room3.setImgURL("http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg");
//                Room r3 = roomRepository.save(room3);
//                Schedule schedule3 = new Schedule();
//                schedule3.setBranch(branch1);
//                schedule3.setMovie(venom);
//                schedule3.setRoom(r3);
//                schedule3.setStartDate(LocalDate.parse("2021-01-05"));
//                schedule3.setStartTime(LocalTime.parse("10:15"));
//                schedule3.setPrice(70000);
//                scheduleRepository.save(schedule3);
//
//                Schedule schedule88 = new Schedule();
//                schedule88.setBranch(branch1);
//                schedule88.setMovie(nguoiNhen);
//                schedule88.setRoom(r3);
//                schedule88.setStartDate(LocalDate.parse("2021-01-05"));
//                schedule88.setStartTime(LocalTime.parse("16:20"));
//                schedule88.setPrice(70000);
//                scheduleRepository.save(schedule88);
//
//                Room room4 = new Room();
//                room4.setName("Ph??ng 404");
//                room4.setBranch(branch1);
//                room4.setCapacity(40);
//                room4.setTotalArea(80);
//                room4.setImgURL("http://hdradio.vn/upload/hinhanh/Cinema-tong-hop/cinema-thiet-ke/Cinema-kd100/cinema-hdradio.jpg");
//                Room r4 = roomRepository.save(room4);
//
//                Schedule schedule99 = new Schedule();
//                schedule99.setBranch(branch1);
//                schedule99.setMovie(nguoiNhen);
//                schedule99.setRoom(r4);
//                schedule99.setStartDate(LocalDate.parse("2021-01-05"));
//                schedule99.setStartTime(LocalTime.parse("16:20"));
//                schedule99.setPrice(70000);
//                scheduleRepository.save(schedule99);
//
//                Branch branch2 = new Branch();
//                branch2.setName("GOLDENNEW Th??? ?????c");
//                branch2.setAddress("216 ??. V?? V??n Ng??n, B??nh Th???, Th??? ?????c, Th??nh ph??? H??? Ch?? Minh");
//                branch2.setPhoneNo("1900 6017");
//                branch2.setImgURL("https://www.google.com/maps/vt/data=TZeUNl_xwzxmDpHYWKkbDv_7amlZzoi4kaRvCEATRTtis3KKxsH0tcFvyiR7Gjt4G3NufaHQaPOcn3pMPNABNgbW2ZoipmmEo6PKNCFhm8CuQbuASrxxaRyviyUG78mD1AVOf1D2fJxIjyEmphS20Wo9dgRW8TJBXekAhiaiGu8g");
//                branch2 = branchRepository.save(branch2);
//                room1.setBranch(branch2);
//                room2.setBranch(branch2);
//                room3.setBranch(branch2);
//                Room r5 = roomRepository.save(room1);
//                Room r6 = roomRepository.save(room2);
//                Room r7 = roomRepository.save(room3);
//                Schedule schedule11 = new Schedule();
//                schedule11.setBranch(branch2);
//                schedule11.setMovie(nguoiNhen);
//                schedule11.setRoom(r5);
//                schedule11.setStartDate(LocalDate.parse("2021-01-05"));
//                schedule11.setStartTime(LocalTime.parse("10:15"));
//                schedule11.setPrice(70000);
//                scheduleRepository.save(schedule11);
//
//
//                Branch branch3 = new Branch();
//                branch3.setName("GOLDENNEW Ba ????nh");
//                branch3.setAddress("29 P. Li???u Giai, Ng???c Kh??nh, Ba ????nh, H?? N???i 100000");
//                branch3.setPhoneNo("1900 6017");
//                branch3.setImgURL("https://www.google.com/maps/vt/data=yhfVddn9tdyWNfmuCzyFU_TR8pm30CLi5oeQTFnFB7qV90WT3OL_ETKkEjQjn3j6zlMuz-VSN_AgZRDCghvF5y2JCVivnwi-sOuKKWT4bSEOf0FZ2-nwoNYSRZH--yH_VpazHsQ9huADdpfR_j3ZnNMEfU_hwJXzSef8AcxHTcqA");
//                branch3 = branchRepository.save(branch3);
//                room1.setBranch(branch3);
//                room2.setBranch(branch3);
//                room3.setBranch(branch3);
//                room4.setBranch(branch3);
//                Room r8= roomRepository.save(room1);
//                Room r9 = roomRepository.save(room2);
//                Room r10 = roomRepository.save(room3);
//                Room r11 = roomRepository.save(room4);
//                Schedule schedule12 = new Schedule();
//                schedule12.setBranch(branch3);
//                schedule12.setMovie(nguoiNhen);
//                schedule12.setRoom(r8);
//                schedule12.setStartDate(LocalDate.parse("2021-01-05"));
//                schedule12.setStartTime(LocalTime.parse("10:15"));
//                schedule12.setPrice(70000);
//                scheduleRepository.save(schedule12);
//                Schedule schedule13 = new Schedule();
//                schedule13.setBranch(branch3);
//                schedule13.setMovie(blackPink);
//                schedule13.setRoom(r9);
//                schedule13.setStartDate(LocalDate.parse("2021-01-05"));
//                schedule13.setStartTime(LocalTime.parse("22:15"));
//                schedule13.setPrice(70000);
//                scheduleRepository.save(schedule12);
//
//                Branch branch4 = new Branch();
//                branch4.setName("GOLDENNEW Ph???m H??ng");
//                branch4.setAddress("Ph???m H??ng, M??? ????nh, Nam T??? Li??m, H?? N???i");
//                branch4.setPhoneNo("1900 6017");
//                branch4.setImgURL("https://www.google.com/maps/vt/data=YVeSKtAT_T4Tie7xjlIh8lVV_CPmpsr36ayQG-gTEGBZtEKRSSUuLnFbj1HBbGxrYJUS3T3ib8llvuVuiSE85HJYK54JW899mhPMP0BWDwBch-utK9g-_kUPd2rsaEpMLmwUd3R9SO67_S6eUEcY0SfqeXAfB2p9NizW8eGSgD63");
//                branch4 = branchRepository.save(branch4);
//                room1.setBranch(branch4);
//                room2.setBranch(branch4);
//                room3.setBranch(branch4);
//                room4.setBranch(branch4);
//                Room r12 = roomRepository.save(room1);
//                roomRepository.save(room2);
//                roomRepository.save(room3);
//                roomRepository.save(room4);
//
//                Schedule schedule14 = new Schedule();
//                schedule14.setBranch(branch4);
//                schedule14.setMovie(nguoiNhen);
//                schedule14.setRoom(r12);
//                schedule14.setStartDate(LocalDate.parse("2021-01-05"));
//                schedule14.setStartTime(LocalTime.parse("10:15"));
//                schedule14.setPrice(70000);
//                scheduleRepository.save(schedule14);
//            }
//        }
//
//
//
//
//    }
//
//    public Movie addNewMovie(
//            String name,
//            String smallImageURl,
//            String shortDescription,
//            String longDescription,
//            String largeImageURL,
//            String director,
//            String actors,
//            String categories,
//            LocalDate releaseDate,
//            int duration,
//            String trailerURL,
//            String language,
//            String rated,
//            int isShowing) {
//        Movie movie = new Movie();
//        movie.setName(name);
//        movie.setSmallImageURl(smallImageURl);
//        movie.setShortDescription(shortDescription);
//        movie.setLongDescription(longDescription);
//        movie.setLargeImageURL(largeImageURL);
//        movie.setDirector(director);
//        movie.setActors(actors);
//        movie.setCategories(categories);
//        movie.setReleaseDate(releaseDate);
//        movie.setDuration(duration);
//        movie.setTrailerURL(trailerURL);
//        movie.setLanguage(language);
//        movie.setRated(rated);
//        movie.setIsShowing(isShowing);
//        movie = movieRepository.save(movie);
//        return movie;
//    }


}
