package com.campusconnect.config;

import com.campusconnect.model.*;
import com.campusconnect.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;
    private final ClubRepository clubRepository;
    private final ClubMembershipRepository membershipRepository;
    private final EventRepository eventRepository;
    private final EventRegistrationRepository registrationRepository;
    private final NoticeRepository noticeRepository;
    private final FeedbackQueryRepository feedbackRepository;

    public DataInitializer(UserRepository userRepository, DepartmentRepository departmentRepository,
                           CourseRepository courseRepository, ClubRepository clubRepository,
                           ClubMembershipRepository membershipRepository, EventRepository eventRepository,
                           EventRegistrationRepository registrationRepository, NoticeRepository noticeRepository,
                           FeedbackQueryRepository feedbackRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.courseRepository = courseRepository;
        this.clubRepository = clubRepository;
        this.membershipRepository = membershipRepository;
        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
        this.noticeRepository = noticeRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            return; // Data already seeded
        }

        System.out.println("🌱 Seeding Campus Connect Demo Data...");

        // 1. Seed Users
        User admin = new User(null, "Dr. Rajesh Raman", "admin@campusconnect.edu", "admin123", "ADMIN", "Administration", "EMP-ADM-01", "+91 98765 43210");
        admin.setBio("Campus Dean & Chief Academic Director");
        admin.setAvatarUrl("https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150&auto=format&fit=crop&q=80");
        userRepository.save(admin);

        User faculty = new User(null, "Prof. Ananya Sharma", "faculty@campusconnect.edu", "faculty123", "FACULTY", "Computer Science & Engineering", "FAC-CS-104", "+91 98111 22334");
        faculty.setBio("Associate Professor & Head of Technical Events Committee");
        faculty.setAvatarUrl("https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=150&auto=format&fit=crop&q=80");
        userRepository.save(faculty);

        User student1 = new User(null, "Rahul Verma", "student@campusconnect.edu", "student123", "STUDENT", "Computer Science & Engineering", "22CS045", "+91 98450 11223");
        student1.setBio("3rd Year CSE Undergrad | Lead at ByteCraft Coding Club");
        student1.setAvatarUrl("https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?w=150&auto=format&fit=crop&q=80");
        User savedStudent = userRepository.save(student1);

        User student2 = new User(null, "Priya Patel", "priya@campusconnect.edu", "student123", "STUDENT", "Electronics & Communication", "22EC012", "+91 98765 12345");
        student2.setBio("Robotics enthusiast & Cultural Society coordinator");
        student2.setAvatarUrl("https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150&auto=format&fit=crop&q=80");
        userRepository.save(student2);

        // 2. Seed Departments
        departmentRepository.save(new Department(null, "CSE", "Computer Science & Engineering", "Leading department in Artificial Intelligence, Software Systems, and Data Engineering.", "Prof. Ananya Sharma", "Alan Turing Block - 3rd Floor", "cse@campusconnect.edu", 480, 24));
        departmentRepository.save(new Department(null, "ECE", "Electronics & Communication", "Specialized in VLSI design, Embedded Systems, IoT, and Signal Processing.", "Dr. K. S. Venkatesh", "JC Bose Block - 2nd Floor", "ece@campusconnect.edu", 360, 18));
        departmentRepository.save(new Department(null, "MECH", "Mechanical Engineering", "Focusing on Robotics, Thermodynamics, Mechatronics, and Industrial Design.", "Dr. Ramesh Menon", "Sir Visvesvaraya Hall", "mech@campusconnect.edu", 300, 15));
        departmentRepository.save(new Department(null, "MBA", "Department of Management Studies", "Fostering leadership, Business Analytics, Finance, and Entrepreneurship.", "Dr. Shalini Saxena", "Management Tower - Ground Floor", "mba@campusconnect.edu", 240, 12));

        // 3. Seed Courses
        courseRepository.save(new Course(null, "CS301", "Data Structures & Algorithms", "Computer Science & Engineering", 4, 3, "Prof. Ananya Sharma", "Core principles of computational efficiency, graph algorithms, and trees."));
        courseRepository.save(new Course(null, "CS402", "Full Stack Cloud & Web Architecture", "Computer Science & Engineering", 4, 5, "Prof. Vikram Reddy", "Modern web engineering using Spring Boot, React, RESTful APIs, and cloud deployments."));
        courseRepository.save(new Course(null, "CS505", "Machine Learning & Neural Networks", "Computer Science & Engineering", 3, 6, "Dr. Sunita Rao", "Supervised learning, deep neural nets, NLP, and model evaluation."));
        courseRepository.save(new Course(null, "EC304", "Digital Signal Processing", "Electronics & Communication", 4, 4, "Dr. K. S. Venkatesh", "Discrete-time signals, Fourier transforms, and digital filter architectures."));
        courseRepository.save(new Course(null, "MB601", "Strategic Business Analytics", "Department of Management Studies", 3, 2, "Dr. Shalini Saxena", "Data-driven business decision making, KPI modeling, and financial analytics."));

        // 4. Seed Clubs
        Club c1 = clubRepository.save(new Club(null, "ByteCraft Coding Club", "TECHNICAL", "The official programming, open source, and hackathon guild of the campus.", "Rahul Verma", "rahul.verma@campusconnect.edu", "Prof. Ananya Sharma", "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=600&auto=format&fit=crop&q=80", "terminal", 185));
        Club c2 = clubRepository.save(new Club(null, "RoboX Robotics Guild", "TECHNICAL", "Building autonomous rovers, drones, and competing in National RoboWars.", "Priya Patel", "priya@campusconnect.edu", "Dr. K. S. Venkatesh", "https://images.unsplash.com/photo-1485827404703-89b55fcc595e?w=600&auto=format&fit=crop&q=80", "cpu", 120));
        Club c3 = clubRepository.save(new Club(null, "Tarang Cultural & Arts Society", "CULTURAL", "Vibrant community for dance, music bands, theater, and fine arts festivals.", "Sneha Nair", "sneha.nair@campusconnect.edu", "Dr. Shalini Saxena", "https://images.unsplash.com/photo-1469488865564-c2de10f69f96?w=600&auto=format&fit=crop&q=80", "music", 230));
        Club c4 = clubRepository.save(new Club(null, "Campus Titans Sports Club", "SPORTS", "Organizing inter-college leagues, athletic meets, cricket, basketball, and football.", "Aman Joshi", "aman.joshi@campusconnect.edu", "Coach R. Singh", "https://images.unsplash.com/photo-1526676037777-05a232554f77?w=600&auto=format&fit=crop&q=80", "trophy", 195));

        // Club Membership
        membershipRepository.save(new ClubMembership(c1.getId(), c1.getName(), savedStudent.getId(), savedStudent.getName(), savedStudent.getEmail(), savedStudent.getDepartment(), "LEAD", "APPROVED"));
        membershipRepository.save(new ClubMembership(c2.getId(), c2.getName(), savedStudent.getId(), savedStudent.getName(), savedStudent.getEmail(), savedStudent.getDepartment(), "MEMBER", "APPROVED"));

        // 5. Seed Events
        Event e1 = new Event(null, "InnovateX 2026: 36-Hour National Hackathon",
                "Join over 500+ brilliant developers to build transformative AI, Web3, and IoT solutions. Prizes worth 2,00,000 INR, mentorship by industry leaders, free food & swag!",
                "HACKATHON", "OFFLINE", "Main Auditorium & Innovation Lab",
                LocalDateTime.now().plusDays(4).withHour(9).withMinute(0),
                LocalDateTime.now().plusDays(5).withHour(21).withMinute(0),
                LocalDateTime.now().plusDays(3).withHour(23).withMinute(59),
                250, 184, 0.0,
                "https://images.unsplash.com/photo-1504384308090-c894fdcc538d?w=800&auto=format&fit=crop&q=80",
                "Rahul Verma", "bytecraft@campusconnect.edu", "ByteCraft Coding Club", "UPCOMING", true);
        Event savedE1 = eventRepository.save(e1);

        Event e2 = new Event(null, "TARANG 2026: Annual Inter-College Cultural Fest",
                "The grandest 3-day extravaganza of music, celebrity concert, fashion night, street play, battle of bands, and culinary stalls.",
                "FEST", "OFFLINE", "Campus Central Grounds",
                LocalDateTime.now().plusDays(12).withHour(16).withMinute(0),
                LocalDateTime.now().plusDays(15).withHour(22).withMinute(0),
                LocalDateTime.now().plusDays(10).withHour(23).withMinute(59),
                1200, 890, 150.0,
                "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?w=800&auto=format&fit=crop&q=80",
                "Sneha Nair", "tarang@campusconnect.edu", "Tarang Cultural Society", "UPCOMING", true);
        Event savedE2 = eventRepository.save(e2);

        Event e3 = new Event(null, "Cloud DevOps & Kubernetes Masterclass",
                "Hands-on workshop on CI/CD pipelines, Docker containerization, Kubernetes cluster management, and AWS/GCP architecture.",
                "WORKSHOP", "HYBRID", "Seminar Hall B & Live Stream",
                LocalDateTime.now().plusDays(7).withHour(10).withMinute(0),
                LocalDateTime.now().plusDays(7).withHour(15).withMinute(0),
                LocalDateTime.now().plusDays(6).withHour(18).withMinute(0),
                150, 132, 0.0,
                "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=800&auto=format&fit=crop&q=80",
                "Prof. Vikram Reddy", "devops@campusconnect.edu", "Computer Science & Engineering", "UPCOMING", true);
        eventRepository.save(e3);

        Event e4 = new Event(null, "Inter-College Basketball Championship",
                "Annual high-stakes tournament featuring top 16 collegiate teams from across the state. Exciting matches, cheer squads, and trophies!",
                "SPORTS", "OFFLINE", "Campus Indoor Sports Complex Court 1",
                LocalDateTime.now().plusDays(9).withHour(8).withMinute(30),
                LocalDateTime.now().plusDays(11).withHour(19).withMinute(0),
                LocalDateTime.now().plusDays(8).withHour(20).withMinute(0),
                300, 210, 0.0,
                "https://images.unsplash.com/photo-1546519638-68e109498ffc?w=800&auto=format&fit=crop&q=80",
                "Aman Joshi", "sports@campusconnect.edu", "Campus Titans Sports Club", "UPCOMING", false);
        eventRepository.save(e4);

        Event e5 = new Event(null, "International AI & Robotics Symposium",
                "Keynotes by IEEE fellows, peer-reviewed paper presentations, and live robotics hardware demonstrations.",
                "SEMINAR", "OFFLINE", "Conference Hall 1",
                LocalDateTime.now().plusDays(18).withHour(9).withMinute(30),
                LocalDateTime.now().plusDays(19).withHour(17).withMinute(0),
                LocalDateTime.now().plusDays(15).withHour(23).withMinute(59),
                180, 95, 200.0,
                "https://images.unsplash.com/photo-1485827404703-89b55fcc595e?w=800&auto=format&fit=crop&q=80",
                "Dr. K. S. Venkatesh", "symposium@campusconnect.edu", "RoboX Robotics Guild", "UPCOMING", true);
        eventRepository.save(e5);

        // 6. Seed Event Registrations (Passes)
        EventRegistration reg1 = new EventRegistration();
        reg1.setEventId(savedE1.getId());
        reg1.setEventTitle(savedE1.getTitle());
        reg1.setEventCategory(savedE1.getCategory());
        reg1.setEventStartDateTime(savedE1.getStartDateTime());
        reg1.setEventVenue(savedE1.getVenue());
        reg1.setStudentId(savedStudent.getId());
        reg1.setStudentName(savedStudent.getName());
        reg1.setStudentEmail(savedStudent.getEmail());
        reg1.setDepartment(savedStudent.getDepartment());
        reg1.setRollNumber(savedStudent.getRollNumberOrEmpId());
        reg1.setPhone(savedStudent.getPhone());
        reg1.setTicketCode("CC-HACK-8492-01");
        reg1.setStatus("CONFIRMED");
        reg1.setAmountPaid(0.0);
        reg1.setPaymentStatus("FREE");
        reg1.setRegisteredAt(LocalDateTime.now().minusDays(1));
        registrationRepository.save(reg1);

        EventRegistration reg2 = new EventRegistration();
        reg2.setEventId(savedE2.getId());
        reg2.setEventTitle(savedE2.getTitle());
        reg2.setEventCategory(savedE2.getCategory());
        reg2.setEventStartDateTime(savedE2.getStartDateTime());
        reg2.setEventVenue(savedE2.getVenue());
        reg2.setStudentId(savedStudent.getId());
        reg2.setStudentName(savedStudent.getName());
        reg2.setStudentEmail(savedStudent.getEmail());
        reg2.setDepartment(savedStudent.getDepartment());
        reg2.setRollNumber(savedStudent.getRollNumberOrEmpId());
        reg2.setPhone(savedStudent.getPhone());
        reg2.setTicketCode("CC-FEST-9931-02");
        reg2.setStatus("CONFIRMED");
        reg2.setAmountPaid(150.0);
        reg2.setPaymentStatus("PAID");
        reg2.setRegisteredAt(LocalDateTime.now().minusHours(12));
        registrationRepository.save(reg2);

        // 7. Seed Notices
        Notice n1 = new Notice(null, "📢 Final Semester Examination Schedule & Hall Tickets Released",
                "The end-semester examinations for all undergraduate and postgraduate engineering branches will commence from next month. Hall tickets can be downloaded from the student portal starting tomorrow.",
                "EXAM", "HIGH", "Controller of Examinations", "ALL", true);
        noticeRepository.save(n1);

        Notice n2 = new Notice(null, "💼 Campus Placement Drive: Microsoft & Google Registrations Open",
                "Eligible 3rd & 4th year CSE/ECE students with CGPA >= 7.5 are requested to submit updated resumes and complete coding profile verification before Friday.",
                "PLACEMENT", "HIGH", "Training & Placement Cell", "STUDENTS", true);
        noticeRepository.save(n2);

        Notice n3 = new Notice(null, "🏆 Call for Nominations: Annual Student Leadership & Best Project Awards",
                "Nominations are invited for the prestigious Chancellor's Gold Medal and Outstanding Capstone Project 2026. Submit your project portfolios to the respective HODs.",
                "ACADEMIC", "MEDIUM", "Dean Academic Affairs", "ALL", false);
        noticeRepository.save(n3);

        Notice n4 = new Notice(null, "⚡ Scheduled Maintenance: Campus Wi-Fi & Central Server Upgrade",
                "Please note that the campus core network will undergo routine firmware maintenance this Saturday between 11:00 PM and 04:00 AM.",
                "GENERAL", "LOW", "IT Infrastructure Committee", "ALL", false);
        noticeRepository.save(n4);

        // 8. Seed Feedback / Grievances
        FeedbackQuery f1 = new FeedbackQuery();
        f1.setStudentName("Rahul Verma");
        f1.setStudentEmail("student@campusconnect.edu");
        f1.setDepartment("Computer Science & Engineering");
        f1.setCategory("INFRASTRUCTURE");
        f1.setSubject("Need additional high-GPU nodes in Innovation AI Lab for Hackathon");
        f1.setMessage("Our hackathon teams require additional compute resources for training deep learning models. Requesting IT department to grant access to the CUDA cluster.");
        f1.setStatus("RESOLVED");
        f1.setAdminResponse("Access granted! 4 additional NVIDIA RTX A6000 nodes have been allocated to the ByteCraft Hackathon pool.");
        f1.setSubmittedAt(LocalDateTime.now().minusDays(2));
        f1.setResolvedAt(LocalDateTime.now().minusDays(1));
        feedbackRepository.save(f1);

        FeedbackQuery f2 = new FeedbackQuery();
        f2.setStudentName("Priya Patel");
        f2.setStudentEmail("priya@campusconnect.edu");
        f2.setDepartment("Electronics & Communication");
        f2.setCategory("EVENT");
        f2.setSubject("Request for extended auditorium rehearsal hours for Tarang Fest");
        f2.setMessage("The cultural dance and musical drama teams need the main auditorium stage between 6 PM to 9 PM during this week.");
        f2.setStatus("IN_REVIEW");
        f2.setSubmittedAt(LocalDateTime.now().minusHours(8));
        feedbackRepository.save(f2);

        System.out.println("✅ Campus Connect Demo Data successfully initialized!");
    }
}
