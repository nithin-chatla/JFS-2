/**
 * CAMPUS CONNECT - COMPLETE FRONTEND CONTROLLER & INTERACTIVITY
 */

const CampusApp = (() => {
  // Pre-configured Demo Users for instant seamless switching
  const DEMO_USERS = {
    admin: {
      id: 1,
      name: "Dr. Rajesh Raman",
      email: "admin@campusconnect.edu",
      role: "ADMIN",
      department: "Administration",
      rollOrEmpId: "EMP-ADM-01",
      phone: "+91 98765 43210",
      avatar: "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150&auto=format&fit=crop&q=80",
      roleText: "Campus Dean • Chief Administrator"
    },
    faculty: {
      id: 2,
      name: "Prof. Ananya Sharma",
      email: "faculty@campusconnect.edu",
      role: "FACULTY",
      department: "Computer Science & Engineering",
      rollOrEmpId: "FAC-CS-104",
      phone: "+91 98111 22334",
      avatar: "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=150&auto=format&fit=crop&q=80",
      roleText: "Associate Professor • CSE Dept"
    },
    student: {
      id: 3,
      name: "Rahul Verma",
      email: "student@campusconnect.edu",
      role: "STUDENT",
      department: "Computer Science & Engineering",
      rollOrEmpId: "22CS045",
      phone: "+91 98450 11223",
      avatar: "https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?w=150&auto=format&fit=crop&q=80",
      roleText: "B.Tech CSE • 3rd Year"
    }
  };

  // State
  let currentUser = DEMO_USERS.student;
  let activeTab = "dashboard";
  let eventsList = [];
  let departmentsList = [];
  let coursesList = [];
  let clubsList = [];
  let noticesList = [];
  let queriesList = [];
  let userRegistrations = [];
  
  // Charts
  let categoryChartInstance = null;
  let deptChartInstance = null;

  // Initialize
  function init() {
    setupLucide();
    switchUserRole("student");
    loadAllData();
  }

  function setupLucide() {
    if (window.lucide) {
      lucide.createIcons();
    }
  }

  // Role Switcher
  function switchUserRole(roleKey) {
    currentUser = DEMO_USERS[roleKey] || DEMO_USERS.student;
    
    // Update Header and Sidebar Profile
    const roleBadge = document.getElementById("current-role-badge");
    if (roleBadge) {
      roleBadge.textContent = currentUser.role;
      roleBadge.className = `role-badge role-${currentUser.role.toLowerCase()}`;
    }

    document.getElementById("user-name").textContent = currentUser.name;
    document.getElementById("user-role-text").textContent = currentUser.roleText;
    document.getElementById("user-avatar").src = currentUser.avatar;

    // Adjust UI permissions
    const btnCreateEvent = document.getElementById("btn-create-event-top");
    const btnPostNotice = document.getElementById("btn-post-notice-top");

    if (currentUser.role === "STUDENT") {
      if (btnCreateEvent) btnCreateEvent.style.display = "none";
      if (btnPostNotice) btnPostNotice.style.display = "none";
    } else {
      if (btnCreateEvent) btnCreateEvent.style.display = "inline-flex";
      if (btnPostNotice) btnPostNotice.style.display = "inline-flex";
    }

    // Pre-fill student feedback fields
    const fbName = document.getElementById("fb-name");
    const fbEmail = document.getElementById("fb-email");
    if (fbName) fbName.value = currentUser.name;
    if (fbEmail) fbEmail.value = currentUser.email;

    showToast(`Switched view to ${currentUser.role}: ${currentUser.name}`, "info");

    // Refresh active tab views
    loadMyTickets();
    loadDashboardStats();
  }

  // Navigation
  function navigate(tabId) {
    activeTab = tabId;

    // Update Nav Buttons
    document.querySelectorAll(".nav-item").forEach(btn => {
      if (btn.dataset.tab === tabId) {
        btn.classList.add("active");
      } else {
        btn.classList.remove("active");
      }
    });

    // Update Content Tabs
    document.querySelectorAll(".tab-content").forEach(sec => {
      sec.classList.remove("active");
    });
    const target = document.getElementById(`tab-${tabId}`);
    if (target) {
      target.classList.add("active");
    }

    // Update Titles
    const titles = {
      dashboard: { title: "Dashboard & Analytics", sub: "Unified metrics and campus ecosystem overview" },
      events: { title: "Campus Events Hub", sub: "Discover, register and participate in fests, hackathons & seminars" },
      "my-passes": { title: "My Event Passes & QR", sub: "Your digital tickets ready for campus entry & verification" },
      clubs: { title: "Clubs & Societies", sub: "Student organizations, tech guilds and cultural chapters" },
      departments: { title: "Departments & Courses", sub: "Academic faculty, department directory and syllabus catalog" },
      notices: { title: "Notice Board & Circulars", sub: "Official announcements, exam timetables & alerts" },
      feedback: { title: "Helpdesk & Grievance Portal", sub: "Submit queries, infrastructure requests and track redressal" },
      reports: { title: "Data Exports & Reports", sub: "Download audit CSV reports and administrative summaries" }
    };

    if (titles[tabId]) {
      document.getElementById("page-title").textContent = titles[tabId].title;
      document.getElementById("page-subtitle").textContent = titles[tabId].sub;
    }

    setupLucide();

    // Trigger tab specific refresh
    if (tabId === "dashboard") loadDashboardStats();
    if (tabId === "events") loadEvents();
    if (tabId === "my-passes") loadMyTickets();
    if (tabId === "clubs") loadClubs();
    if (tabId === "departments") { loadDepartments(); loadCourses(); }
    if (tabId === "notices") loadNotices();
    if (tabId === "feedback") loadQueries();
  }

  // Load All Core Data
  async function loadAllData() {
    await Promise.all([
      loadDashboardStats(),
      loadEvents(),
      loadMyTickets(),
      loadClubs(),
      loadDepartments(),
      loadCourses(),
      loadNotices(),
      loadQueries()
    ]);
  }

  // Dashboard Stats & Charts
  async function loadDashboardStats() {
    try {
      const res = await fetch("/api/analytics/dashboard");
      if (!res.ok) throw new Error("Failed to fetch dashboard stats");
      const data = await res.json();

      document.getElementById("stat-total-events").textContent = data.totalEvents || 5;
      document.getElementById("stat-total-registrations").textContent = Number(data.totalRegistrations || 1511).toLocaleString();
      document.getElementById("stat-total-students").textContent = Number(data.totalStudents || 1380).toLocaleString();
      document.getElementById("stat-total-clubs").textContent = data.totalClubs || 4;

      // Update Nav Badges
      const nEvt = document.getElementById("nav-events-count");
      if (nEvt) nEvt.textContent = data.totalEvents || 5;

      const nNot = document.getElementById("nav-notices-count");
      if (nNot) nNot.textContent = data.activeNotices || 4;

      renderCharts(data);
      renderTopEvents(data.topPopularEvents || []);
      renderRecentActivities(data.recentActivities || []);
    } catch (err) {
      console.warn("Analytics fetch error, using fallback state:", err);
    }
  }

  function renderCharts(data) {
    const categoryCtx = document.getElementById("categoryChart");
    if (categoryCtx) {
      if (categoryChartInstance) categoryChartInstance.destroy();

      const catLabels = data.eventsByCategory ? Object.keys(data.eventsByCategory) : ["Hackathon", "Fest", "Workshop", "Sports", "Seminar"];
      const catValues = data.eventsByCategory ? Object.values(data.eventsByCategory) : [1, 1, 1, 1, 1];

      categoryChartInstance = new Chart(categoryCtx, {
        type: "doughnut",
        data: {
          labels: catLabels,
          datasets: [{
            data: catValues,
            backgroundColor: ["#6366f1", "#a855f7", "#06b6d4", "#10b981", "#f59e0b", "#ec4899"],
            borderWidth: 0
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            legend: { position: "right", labels: { color: "#94a3b8", font: { family: "Plus Jakarta Sans", size: 11 } } }
          },
          cutout: "70%"
        }
      });
    }

    const deptCtx = document.getElementById("deptChart");
    if (deptCtx) {
      if (deptChartInstance) deptChartInstance.destroy();

      const deptLabels = ["CSE", "ECE", "MECH", "MBA"];
      const deptValues = [480, 360, 300, 240];

      deptChartInstance = new Chart(deptCtx, {
        type: "bar",
        data: {
          labels: deptLabels,
          datasets: [{
            label: "Enrolled Students",
            data: deptValues,
            backgroundColor: "rgba(99, 102, 241, 0.8)",
            borderRadius: 6
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          plugins: { legend: { display: false } },
          scales: {
            x: { grid: { display: false }, ticks: { color: "#94a3b8" } },
            y: { grid: { color: "rgba(255,255,255,0.05)" }, ticks: { color: "#94a3b8" } }
          }
        }
      });
    }
  }

  function renderTopEvents(list) {
    const container = document.getElementById("top-events-list");
    if (!container) return;

    if (!list || list.length === 0) {
      container.innerHTML = `<p class="text-dim">No events data available</p>`;
      return;
    }

    container.innerHTML = list.map(evt => `
      <div class="top-event-row">
        <div class="top-event-info">
          <h4>${evt.title}</h4>
          <div class="top-event-meta">
            <span><i data-lucide="tag"></i> ${evt.category}</span>
            <span><i data-lucide="users"></i> ${evt.registeredCount || 0} / ${evt.capacity} Registered</span>
          </div>
        </div>
        <div class="progress-pill">${evt.fillRate || 0}% Full</div>
      </div>
    `).join("");

    setupLucide();
  }

  function renderRecentActivities(list) {
    const container = document.getElementById("recent-activity-list");
    if (!container) return;

    if (!list || list.length === 0) {
      container.innerHTML = `
        <div class="activity-item">
          <div class="activity-dot"></div>
          <div class="activity-content">
            <h5>Rahul Verma registered for InnovateX 2026</h5>
            <span class="activity-time">Just now • Pass: CC-HACK-8492-01</span>
          </div>
        </div>
      `;
      return;
    }

    container.innerHTML = list.map(act => `
      <div class="activity-item">
        <div class="activity-dot"></div>
        <div class="activity-content">
          <h5>${act.title}</h5>
          <span class="activity-time">Pass Code: <strong>${act.ticketCode || 'CC-REG'}</strong></span>
        </div>
      </div>
    `).join("");
  }

  // Events Hub
  async function loadEvents(category = "ALL", search = "") {
    try {
      let url = "/api/events";
      const params = new URLSearchParams();
      if (category && category !== "ALL") params.append("category", category);
      if (search) params.append("search", search);
      if (params.toString()) url += "?" + params.toString();

      const res = await fetch(url);
      eventsList = await res.json();
      renderEventsGrid(eventsList);
    } catch (err) {
      console.error("Error loading events:", err);
    }
  }

  function renderEventsGrid(list) {
    const container = document.getElementById("events-grid-container");
    if (!container) return;

    if (!list || list.length === 0) {
      container.innerHTML = `<div class="empty-state"><h3>No events found</h3><p>Try clearing filters or search keywords.</p></div>`;
      return;
    }

    container.innerHTML = list.map(evt => {
      const isRegistered = userRegistrations.some(r => r.eventId === evt.id && r.status !== 'CANCELLED');
      const fillPercent = evt.capacity > 0 ? Math.min(100, Math.round((evt.registeredCount / evt.capacity) * 100)) : 0;
      const feeText = evt.fee > 0 ? `₹${evt.fee}` : "FREE";
      const isFull = evt.registeredCount >= evt.capacity;

      const dateStr = evt.startDateTime ? new Date(evt.startDateTime).toLocaleDateString("en-US", { month: "short", day: "numeric", hour: "2-digit", minute: "2-digit" }) : "TBA";

      return `
        <div class="event-card">
          <div class="event-card-banner">
            <img src="${evt.bannerUrl || 'https://images.unsplash.com/photo-1517245386807-bb43f82c33c4?w=800'}" alt="${evt.title}">
            <span class="event-badge-floating">${evt.category}</span>
            <span class="event-fee-badge ${evt.fee > 0 ? 'paid' : ''}">${feeText}</span>
          </div>
          <div class="event-card-body">
            <h3>${evt.title}</h3>
            <p>${evt.description}</p>
            
            <div class="event-meta-list">
              <div class="meta-item"><i data-lucide="calendar"></i> <span>${dateStr}</span></div>
              <div class="meta-item"><i data-lucide="map-pin"></i> <span>${evt.venue || 'Campus Venue'}</span></div>
              <div class="meta-item"><i data-lucide="shield-check"></i> <span>Organized by ${evt.organizingDepartmentOrClub || evt.organizerName}</span></div>
            </div>

            <div class="event-capacity-bar">
              <div class="capacity-labels">
                <span>Seats Filled</span>
                <span><strong>${evt.registeredCount}</strong> / ${evt.capacity} (${fillPercent}%)</span>
              </div>
              <div class="progress-track">
                <div class="progress-fill" style="width: ${fillPercent}%;"></div>
              </div>
            </div>

            <div class="event-card-footer">
              ${isRegistered ? `
                <button class="btn btn-outline btn-block" onclick="CampusApp.navigate('my-passes')">
                  <i data-lucide="ticket"></i> View Pass
                </button>
              ` : isFull ? `
                <button class="btn btn-glass btn-block" disabled>Sold Out</button>
              ` : `
                <button class="btn btn-primary btn-block" onclick="CampusApp.openRegisterModal(${evt.id})">
                  <i data-lucide="user-check"></i> Register Now
                </button>
              `}
              ${currentUser.role !== 'STUDENT' ? `
                <button class="btn btn-glass btn-sm" title="Edit Event" onclick="CampusApp.editEvent(${evt.id})">
                  <i data-lucide="edit-3"></i>
                </button>
                <button class="btn btn-glass btn-sm" title="Delete Event" onclick="CampusApp.deleteEvent(${evt.id})">
                  <i data-lucide="trash-2"></i>
                </button>
              ` : ''}
            </div>
          </div>
        </div>
      `;
    }).join("");

    setupLucide();
  }

  function filterEventsCategory(cat) {
    document.querySelectorAll("#event-category-filters .filter-pill").forEach(pill => {
      if (pill.textContent.toUpperCase().includes(cat) || (cat === 'ALL' && pill.textContent.includes('All'))) {
        pill.classList.add("active");
      } else {
        pill.classList.remove("active");
      }
    });
    loadEvents(cat);
  }

  // Event Registration Modal
  function openRegisterModal(eventId) {
    const evt = eventsList.find(e => e.id === eventId);
    if (!evt) return;

    document.getElementById("reg-event-id").value = evt.id;
    document.getElementById("reg-modal-event-title").textContent = `Register: ${evt.title}`;
    
    document.getElementById("reg-event-brief").innerHTML = `
      <strong>${evt.title}</strong><br>
      <span class="text-muted"><i data-lucide="map-pin"></i> ${evt.venue} | Fee: ${evt.fee > 0 ? '₹' + evt.fee : 'FREE'}</span>
    `;

    // Auto fill user details
    document.getElementById("reg-name").value = currentUser.name;
    document.getElementById("reg-email").value = currentUser.email;
    document.getElementById("reg-dept").value = currentUser.department;
    document.getElementById("reg-roll").value = currentUser.rollOrEmpId;
    document.getElementById("reg-phone").value = currentUser.phone;

    openModal("register-modal");
    setupLucide();
  }

  async function submitEventRegistration(e) {
    e.preventDefault();
    const eventId = document.getElementById("reg-event-id").value;
    
    const payload = {
      studentId: currentUser.id,
      studentName: document.getElementById("reg-name").value,
      studentEmail: document.getElementById("reg-email").value,
      department: document.getElementById("reg-dept").value,
      rollNumber: document.getElementById("reg-roll").value,
      phone: document.getElementById("reg-phone").value
    };

    try {
      const res = await fetch(`/api/events/${eventId}/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      });

      if (!res.ok) {
        const errorText = await res.text();
        throw new Error(errorText || "Registration failed");
      }

      const reg = await res.json();
      closeModal("register-modal");
      showToast(`🎉 Registration Confirmed! Pass Code: ${reg.ticketCode}`, "success");
      
      await loadEvents();
      await loadMyTickets();
      await loadDashboardStats();
      
      // Open digital pass popup
      showPassModal(reg);
    } catch (err) {
      showToast(err.message, "error");
    }
  }

  // My Event Passes & Digital Tickets
  async function loadMyTickets() {
    try {
      const res = await fetch(`/api/events/student/${currentUser.id}/registrations`);
      if (res.ok) {
        userRegistrations = await res.json();
      } else {
        userRegistrations = [];
      }

      const badge = document.getElementById("nav-tickets-count");
      if (badge) badge.textContent = userRegistrations.length;

      renderMyTickets(userRegistrations);
    } catch (err) {
      console.warn("Could not load student tickets:", err);
    }
  }

  function renderMyTickets(list) {
    const container = document.getElementById("my-tickets-container");
    if (!container) return;

    if (!list || list.length === 0) {
      container.innerHTML = `
        <div class="empty-state">
          <h3>No event passes yet</h3>
          <p>Browse upcoming campus events and register to generate your verified digital QR entry pass!</p>
          <button class="btn btn-primary mt-3" onclick="CampusApp.navigate('events')">Browse Events</button>
        </div>
      `;
      return;
    }

    container.innerHTML = list.map(reg => {
      const dateStr = reg.eventStartDateTime ? new Date(reg.eventStartDateTime).toLocaleDateString("en-US", { month: "short", day: "numeric", hour: "2-digit", minute: "2-digit" }) : "Scheduled";

      return `
        <div class="ticket-pass">
          <div class="ticket-header">
            <span class="ticket-badge">${reg.eventCategory || 'EVENT'}</span>
            <span class="ticket-code">${reg.ticketCode}</span>
          </div>
          <div class="ticket-body">
            <h3>${reg.eventTitle}</h3>
            
            <div class="ticket-details-grid">
              <div>
                <div class="t-label">Attendee</div>
                <div class="t-val">${reg.studentName}</div>
              </div>
              <div>
                <div class="t-label">Roll No / ID</div>
                <div class="t-val">${reg.rollNumber || '22CS045'}</div>
              </div>
              <div>
                <div class="t-label">Date & Time</div>
                <div class="t-val">${dateStr}</div>
              </div>
              <div>
                <div class="t-label">Status</div>
                <div class="t-val text-success">● ${reg.status}</div>
              </div>
            </div>

            <div class="ticket-perforation">
              <div class="qr-box" id="qr-${reg.id}"></div>
              <div class="ticket-instructions">
                <p><strong>Official Entry Pass</strong></p>
                <p>Present this scannable QR pass at the entrance gate.</p>
                <button class="btn btn-glass btn-sm mt-2" onclick="CampusApp.showPassModalById(${reg.id})">
                  <i data-lucide="maximize-2"></i> Fullscreen Pass
                </button>
              </div>
            </div>
          </div>
        </div>
      `;
    }).join("");

    // Generate real QR codes into each ticket
    list.forEach(reg => {
      const qrEl = document.getElementById(`qr-${reg.id}`);
      if (qrEl && window.QRCode) {
        qrEl.innerHTML = "";
        new QRCode(qrEl, {
          text: `CAMPUS-PASS:${reg.ticketCode}:${reg.studentEmail}`,
          width: 70,
          height: 70,
          colorDark: "#0f172a",
          colorLight: "#ffffff",
          correctLevel: QRCode.CorrectLevel.M
        });
      }
    });

    setupLucide();
  }

  function showPassModalById(regId) {
    const reg = userRegistrations.find(r => r.id === regId);
    if (reg) showPassModal(reg);
  }

  function showPassModal(reg) {
    const modalContent = document.getElementById("pass-modal-content");
    if (!modalContent) return;

    modalContent.innerHTML = `
      <div style="background: #fff; color: #000; padding: 24px; border-radius: 12px; margin-bottom: 16px;">
        <div style="font-weight: 800; font-size: 1.2rem; color: #4338ca; margin-bottom: 4px;">CAMPUS CONNECT</div>
        <div style="font-size: 0.75rem; color: #64748b; margin-bottom: 16px;">OFFICIAL EVENT ENTRY E-PASS</div>
        
        <div id="modal-qr-target" style="display: flex; justify-content: center; margin-bottom: 16px;"></div>

        <div style="font-family: monospace; font-size: 1.1rem; font-weight: 700; letter-spacing: 1px; margin-bottom: 12px;">${reg.ticketCode}</div>
        
        <div style="text-align: left; font-size: 0.82rem; border-top: 1px solid #e2e8f0; padding-top: 12px;">
          <div><strong>Event:</strong> ${reg.eventTitle}</div>
          <div><strong>Attendee:</strong> ${reg.studentName} (${reg.rollNumber || 'Student'})</div>
          <div><strong>Venue:</strong> ${reg.eventVenue || 'Campus Main Auditorium'}</div>
          <div><strong>Payment Status:</strong> ${reg.paymentStatus || 'CONFIRMED'}</div>
        </div>
      </div>
    `;

    openModal("pass-modal");

    setTimeout(() => {
      const qrTarget = document.getElementById("modal-qr-target");
      if (qrTarget && window.QRCode) {
        new QRCode(qrTarget, {
          text: `CAMPUS-VERIFY:${reg.ticketCode}:${reg.studentName}`,
          width: 140,
          height: 140,
          colorDark: "#0f172a",
          colorLight: "#ffffff"
        });
      }
    }, 100);
  }

  // Create & Edit Event Modal
  function showEventModal() {
    document.getElementById("evt-id").value = "";
    document.getElementById("evt-title").value = "";
    document.getElementById("evt-description").value = "";
    document.getElementById("evt-venue").value = "";
    document.getElementById("evt-organizer").value = currentUser.department || "CSE Department";
    document.getElementById("evt-organizer-email").value = currentUser.email;

    const now = new Date();
    const startDate = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000).toISOString().slice(0, 16);
    const endDate = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000 + 4 * 60 * 60 * 1000).toISOString().slice(0, 16);
    document.getElementById("evt-start").value = startDate;
    document.getElementById("evt-end").value = endDate;

    openModal("event-modal");
  }

  async function submitCreateEvent(e) {
    e.preventDefault();
    const id = document.getElementById("evt-id").value;
    
    const payload = {
      title: document.getElementById("evt-title").value,
      category: document.getElementById("evt-category").value,
      description: document.getElementById("evt-description").value,
      eventType: document.getElementById("evt-type").value,
      venue: document.getElementById("evt-venue").value,
      startDateTime: document.getElementById("evt-start").value,
      endDateTime: document.getElementById("evt-end").value,
      capacity: parseInt(document.getElementById("evt-capacity").value) || 100,
      fee: parseFloat(document.getElementById("evt-fee").value) || 0.0,
      bannerUrl: document.getElementById("evt-banner").value,
      organizerName: document.getElementById("evt-organizer").value,
      organizerEmail: document.getElementById("evt-organizer-email").value,
      organizingDepartmentOrClub: document.getElementById("evt-organizer").value
    };

    try {
      const url = id ? `/api/events/${id}` : "/api/events";
      const method = id ? "PUT" : "POST";

      const res = await fetch(url, {
        method: method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      });

      if (!res.ok) throw new Error("Failed to save event");

      closeModal("event-modal");
      showToast("Event published successfully!", "success");
      loadEvents();
      loadDashboardStats();
    } catch (err) {
      showToast(err.message, "error");
    }
  }

  async function deleteEvent(id) {
    if (!confirm("Are you sure you want to delete this event?")) return;
    try {
      await fetch(`/api/events/${id}`, { method: "DELETE" });
      showToast("Event deleted", "info");
      loadEvents();
      loadDashboardStats();
    } catch (err) {
      showToast("Error deleting event", "error");
    }
  }

  // Clubs & Societies
  async function loadClubs() {
    try {
      const res = await fetch("/api/clubs");
      clubsList = await res.json();
      renderClubs(clubsList);
    } catch (err) {
      console.error("Error loading clubs:", err);
    }
  }

  function renderClubs(list) {
    const container = document.getElementById("clubs-grid-container");
    if (!container) return;

    container.innerHTML = list.map(club => `
      <div class="club-card">
        <div class="club-card-header">
          <div class="club-icon-circle">
            <i data-lucide="${club.logoIcon === 'cpu' ? 'cpu' : club.logoIcon === 'music' ? 'music' : club.logoIcon === 'trophy' ? 'trophy' : 'terminal'}"></i>
          </div>
          <span class="badge-sub">${club.category}</span>
        </div>
        <h3>${club.name}</h3>
        <p>${club.description}</p>
        
        <div class="club-footer-meta">
          <div>
            <div class="t-label">Lead Coordinator</div>
            <div class="t-val">${club.coordinatorName || 'Student Lead'}</div>
          </div>
          <button class="btn btn-outline btn-sm" onclick="CampusApp.joinClub(${club.id}, '${club.name}')">
            <i data-lucide="user-plus"></i> Join Club
          </button>
        </div>
      </div>
    `).join("");

    setupLucide();
  }

  async function joinClub(clubId, clubName) {
    const payload = {
      clubId: clubId,
      clubName: clubName,
      studentId: currentUser.id,
      studentName: currentUser.name,
      studentEmail: currentUser.email,
      department: currentUser.department,
      roleInClub: "MEMBER",
      status: "APPROVED"
    };

    try {
      const res = await fetch("/api/clubs/join", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      });

      if (!res.ok) {
        const err = await res.text();
        throw new Error(err || "Failed to join club");
      }

      showToast(`🎉 You have successfully joined ${clubName}!`, "success");
      loadClubs();
    } catch (err) {
      showToast(err.message, "error");
    }
  }

  // Academic Directory & Courses
  async function loadDepartments() {
    try {
      const res = await fetch("/api/departments");
      departmentsList = await res.json();
      renderDepartments(departmentsList);
    } catch (err) {
      console.error("Error loading departments:", err);
    }
  }

  function renderDepartments(list) {
    const container = document.getElementById("dept-grid-container");
    if (!container) return;

    container.innerHTML = list.map(dept => `
      <div class="dept-card">
        <div class="dept-card-header">
          <span class="badge-sub">${dept.code}</span>
          <span class="t-label">${dept.building}</span>
        </div>
        <h3>${dept.name}</h3>
        <p>${dept.description}</p>
        <div class="club-footer-meta">
          <div>
            <div class="t-label">Head of Dept</div>
            <div class="t-val">${dept.headOfDepartment}</div>
          </div>
          <div>
            <div class="t-label">Strength</div>
            <div class="t-val">${dept.studentCount} Students</div>
          </div>
        </div>
      </div>
    `).join("");
  }

  async function loadCourses(dept = "ALL") {
    try {
      let url = "/api/courses";
      if (dept && dept !== "ALL") url += `?department=${encodeURIComponent(dept)}`;
      const res = await fetch(url);
      coursesList = await res.json();
      renderCourses(coursesList);
    } catch (err) {
      console.error("Error loading courses:", err);
    }
  }

  function renderCourses(list) {
    const tbody = document.getElementById("courses-table-body");
    if (!tbody) return;

    tbody.innerHTML = list.map(c => `
      <tr>
        <td><strong>${c.code}</strong></td>
        <td>${c.title}</td>
        <td><span class="badge-sub">${c.department}</span></td>
        <td>${c.credits} Credits</td>
        <td>Semester ${c.semester}</td>
        <td>${c.instructorName || 'Faculty'}</td>
        <td>
          <button class="btn btn-glass btn-sm" onclick="alert('Course Syllabus for ${c.code} is available in Academic ERP.')">
            <i data-lucide="file-text"></i> Syllabus
          </button>
        </td>
      </tr>
    `).join("");

    setupLucide();
  }

  function filterCoursesByDept(dept) {
    loadCourses(dept);
  }

  // Notice Board
  async function loadNotices() {
    try {
      const res = await fetch("/api/notices");
      noticesList = await res.json();
      renderNotices(noticesList);
    } catch (err) {
      console.error("Error loading notices:", err);
    }
  }

  function renderNotices(list) {
    const container = document.getElementById("notices-container");
    if (!container) return;

    container.innerHTML = list.map(ntc => {
      const dateStr = ntc.publishedDate ? new Date(ntc.publishedDate).toLocaleDateString("en-US", { month: "short", day: "numeric", year: "numeric" }) : "Recent";

      return `
        <div class="notice-card ${ntc.pinned ? 'pinned' : ''}">
          ${ntc.pinned ? '<div class="notice-pin-badge"><i data-lucide="pin"></i> PINNED NOTICE</div>' : ''}
          <span class="badge-sub ${ntc.priority === 'HIGH' ? 'red' : ''}">${ntc.category} • ${ntc.priority} PRIORITY</span>
          <h3>${ntc.title}</h3>
          <p>${ntc.content}</p>
          <div class="notice-footer">
            <span>Published by: <strong>${ntc.publishedBy || 'Campus Admin'}</strong></span>
            <span>${dateStr}</span>
          </div>
        </div>
      `;
    }).join("");

    setupLucide();
  }

  function showNoticeModal() {
    openModal("notice-modal");
  }

  async function submitNotice(e) {
    e.preventDefault();
    const payload = {
      title: document.getElementById("ntc-title").value,
      category: document.getElementById("ntc-category").value,
      priority: document.getElementById("ntc-priority").value,
      targetAudience: document.getElementById("ntc-audience").value,
      content: document.getElementById("ntc-content").value,
      pinned: document.getElementById("ntc-pinned").checked,
      publishedBy: currentUser.name
    };

    try {
      const res = await fetch("/api/notices", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      });
      if (!res.ok) throw new Error("Failed to post notice");

      closeModal("notice-modal");
      showToast("📢 Notice published to campus board!", "success");
      loadNotices();
      loadDashboardStats();
    } catch (err) {
      showToast(err.message, "error");
    }
  }

  // Helpdesk & Grievance Queries
  async function loadQueries(status = "ALL") {
    try {
      let url = "/api/feedback";
      if (status && status !== "ALL") url += `?status=${status}`;
      const res = await fetch(url);
      queriesList = await res.json();
      renderQueries(queriesList);
    } catch (err) {
      console.error("Error loading queries:", err);
    }
  }

  function renderQueries(list) {
    const container = document.getElementById("queries-list-container");
    if (!container) return;

    if (!list || list.length === 0) {
      container.innerHTML = `<p class="text-dim">No queries found for this filter.</p>`;
      return;
    }

    container.innerHTML = list.map(q => `
      <div class="query-card">
        <div class="query-header">
          <span class="badge-sub">${q.category}</span>
          <span class="query-status-badge ${q.status}">${q.status}</span>
        </div>
        <h4>${q.subject}</h4>
        <p>${q.message}</p>
        <div class="text-dim" style="font-size:0.72rem;">From: ${q.studentName} (${q.studentEmail}) • Dept: ${q.department || 'Student'}</div>
        
        ${q.adminResponse ? `
          <div class="admin-response-box">
            <strong>Admin Resolution:</strong> ${q.adminResponse}
          </div>
        ` : currentUser.role !== 'STUDENT' ? `
          <button class="btn btn-outline btn-sm mt-2" onclick="CampusApp.openRespondModal(${q.id})">
            <i data-lucide="corner-down-right"></i> Respond & Resolve
          </button>
        ` : ''}
      </div>
    `).join("");

    setupLucide();
  }

  function filterQueries(status) {
    loadQueries(status);
  }

  async function handleFeedbackSubmit(e) {
    e.preventDefault();
    const payload = {
      studentName: document.getElementById("fb-name").value,
      studentEmail: document.getElementById("fb-email").value,
      department: document.getElementById("fb-dept").value,
      category: document.getElementById("fb-category").value,
      subject: document.getElementById("fb-subject").value,
      message: document.getElementById("fb-message").value
    };

    try {
      const res = await fetch("/api/feedback", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      });
      if (!res.ok) throw new Error("Failed to submit query");

      document.getElementById("fb-subject").value = "";
      document.getElementById("fb-message").value = "";
      showToast("✅ Query submitted to administration!", "success");
      loadQueries();
    } catch (err) {
      showToast(err.message, "error");
    }
  }

  function openRespondModal(queryId) {
    const query = queriesList.find(q => q.id === queryId);
    if (!query) return;

    document.getElementById("resp-query-id").value = query.id;
    document.getElementById("resp-query-preview").innerHTML = `
      <strong>Query:</strong> ${query.subject}<br>
      <span class="text-muted">${query.message}</span>
    `;
    document.getElementById("resp-query-text").value = "";
    openModal("respond-query-modal");
  }

  async function submitQueryResponse(e) {
    e.preventDefault();
    const id = document.getElementById("resp-query-id").value;
    const response = document.getElementById("resp-query-text").value;
    const status = document.getElementById("resp-query-status").value;

    try {
      const res = await fetch(`/api/feedback/${id}/respond`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ response, status })
      });
      if (!res.ok) throw new Error("Failed to send response");

      closeModal("respond-query-modal");
      showToast("Response recorded and student notified!", "success");
      loadQueries();
      loadDashboardStats();
    } catch (err) {
      showToast(err.message, "error");
    }
  }

  // Search
  function handleGlobalSearch(term) {
    if (activeTab === "events") {
      loadEvents("ALL", term);
    }
  }

  // UI Helpers: Modals & Toasts
  function openModal(modalId) {
    const m = document.getElementById(modalId);
    if (m) m.classList.add("active");
  }

  function closeModal(modalId) {
    const m = document.getElementById(modalId);
    if (m) m.classList.remove("active");
  }

  function toggleSidebar() {
    const sb = document.getElementById("sidebar");
    if (sb) sb.classList.toggle("open");
  }

  function showToast(message, type = "info") {
    const container = document.getElementById("toast-container");
    if (!container) return;

    const toast = document.createElement("div");
    toast.className = `toast ${type}`;
    toast.innerHTML = `<span>${message}</span>`;
    container.appendChild(toast);

    setTimeout(() => {
      toast.style.opacity = "0";
      setTimeout(() => toast.remove(), 300);
    }, 3500);
  }

  // Public API
  return {
    init,
    navigate,
    switchUserRole,
    filterEventsCategory,
    openRegisterModal,
    submitEventRegistration,
    showPassModalById,
    showEventModal,
    submitCreateEvent,
    deleteEvent,
    joinClub,
    filterCoursesByDept,
    showNoticeModal,
    submitNotice,
    filterQueries,
    handleFeedbackSubmit,
    openRespondModal,
    submitQueryResponse,
    handleGlobalSearch,
    openModal,
    closeModal,
    toggleSidebar
  };
})();

// Boot on DOM ready
document.addEventListener("DOMContentLoaded", () => {
  CampusApp.init();
});
