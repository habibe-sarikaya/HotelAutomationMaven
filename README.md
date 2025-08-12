HotelAutomationMaven

**HotelAutomationMaven**, Java (Maven) tabanlı, web arayüzüne sahip bir **otel otomasyon sistemi** projesidir.  
Arka planda Java kullanılırken, ön yüz web teknolojileri ile geliştirilmiştir.

---

## 🚀 Özellikler
- 🔑 Müşteri kayıt ve giriş işlemleri
- 🏠 Oda rezervasyonu ve yönetimi
- 📊 Yönetici paneli
- 🌐 Web tabanlı kullanıcı arayüzü
- ⚙️ Maven proje yapısı

---

## 🛠️ Kurulum & Geliştirme

```bash
# Bağımlılıkları yükle ve paketle
mvn clean package

# Spring Boot kullanıyorsan:
mvn spring-boot:run

# Servlet/JSP tabanlı ise:
mvn jetty:run

🧪 Test Çalıştırma
mvn test

📂 Proje Yapısı
HotelAutomationMaven/
│
├── src/                # Java kaynak kodları
├── target/             # Derlenmiş dosyalar (git'e dahil edilmez)
├── pom.xml             # Maven yapılandırması
└── README.md           # Proje dökümantasyonu

📜 Lisans
Bu proje sadece eğitim amacıyla geliştirilmiştir.
