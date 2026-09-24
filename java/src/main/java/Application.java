import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;


public class Application {

    public static void main(String[] args) throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);

    server.createContext("/", Application::handleRequest);

    server.setExecutor(null);
    server.start();

    System.out.println("Java application running on port 8081");
}

private static void handleRequest(HttpExchange exchange) throws IOException {

        String html = """
                <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bloomy Technologies • Class of 2026 Yearbook</title>
    <style>
        :root {
            --bloomy-blue: #1b4ed8;
            --bloomy-dark: #0f172a;
            --bloomy-purple: #9333ea;
            --bloomy-lavender: #f3e8ff;
            --bg-canvas: #f8fafc;
        }

        * { box-sizing: border-box; margin: 0; padding: 0; }

        body {
            min-height: 100vh;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: var(--bg-canvas);
            color: var(--bloomy-dark);
            padding-bottom: 70px;
        }

        .brand-banner {
            background-color: var(--bloomy-blue);
            padding: 22px 40px;
            display: flex;
            align-items: center;
            gap: 18px;
            box-shadow: 0 4px 12px rgba(27, 78, 216, 0.25);
        }

        .logo-circle {
            width: 58px;
            height: 58px;
            border-radius: 50%;
            background: linear-gradient(135deg, #4a67db, #e9d5ff);
            border: 3px solid #711686;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: 800;
            font-size: 1.7rem;
            color: var(--bloomy-dark);
            position: relative;
            box-shadow: 0 0 0 2px var(--bloomy-purple);
        }

        .logo-dot {
            width: 12px;
            height: 12px;
            background-color: var(--bloomy-purple);
            border-radius: 50%;
            position: absolute;
            top: -2px;
            right: -2px;
            border: 2px solid #ffffff;
        }

        .brand-title h1 {
            color: #ffffff;
            font-size: 1.75rem;
            line-height: 1.15;
            font-weight: 700;
        }

        .yearbook-container {
            max-width: 1200px;
            margin: 48px auto;
            padding: 0 20px;
        }

        .yearbook-header-title {
            text-align: center;
            font-size: 2.1rem;
            margin-bottom: 12px;
            font-weight: 800;
            letter-spacing: -0.5px;
        }

        .yearbook-subtitle {
            text-align: center;
            color: #64748b;
            margin-bottom: 42px;
            font-size: 1.05rem;
        }

        .yearbook-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 28px;
        }

        .trainee-card {
            background: white;
            border-radius: 18px;
            padding: 28px 22px;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.05);
            border: 1px solid #e2e8f0;
            text-align: center;
            transition: all 0.3s ease;
            position: relative;
        }

        .trainee-card:hover {
            transform: translateY(-6px);
            box-shadow: 0 14px 24px -6px rgba(0, 0, 0, 0.12);
        }

        .trainee-card.instructor {
            border: 2px solid var(--bloomy-blue);
            background: linear-gradient(180deg, #f0f7ff 0%, #ffffff 40%);
        }

        .instructor-badge {
            position: absolute;
            top: 14px;
            right: 14px;
            background: var(--bloomy-blue);
            color: white;
            font-size: 0.7rem;
            font-weight: 700;
            padding: 4px 10px;
            border-radius: 20px;
            letter-spacing: 0.4px;
        }

        .photo-box {
            width: 130px;
            height: 130px;
            margin: 0 auto 18px auto;
            border-radius: 50%;
            border: 4px solid var(--bloomy-blue);
            padding: 4px;
            background: white;
        }

        .photo-box img {
            width: 100%;
            height: 100%;
            border-radius: 50%;
            object-fit: cover;
            background-color: #f1f5f9;
        }

        .name {
            font-size: 1.35rem;
            font-weight: 700;
            margin-bottom: 8px;
            color: var(--bloomy-dark);
        }

        .superlative {
            display: inline-block;
            font-size: 0.78rem;
            font-weight: 700;
            text-transform: uppercase;
            color: var(--bloomy-purple);
            background-color: var(--bloomy-lavender);
            padding: 5px 13px;
            border-radius: 20px;
            letter-spacing: 0.4px;
            margin-bottom: 14px;
        }

        .quote {
            font-style: italic;
            font-size: 0.95rem;
            color: #64748b;
            line-height: 1.55;
            min-height: 48px;
        }

        .footer-marquee-container {
            position: fixed;
            bottom: 0;
            left: 0;
            width: 100%;
            background-color: var(--bloomy-blue);
            color: #ffffff;
            padding: 13px 0;
            font-size: 0.9rem;
            font-weight: 600;
            letter-spacing: 0.4px;
            box-shadow: 0 -4px 12px rgba(0, 0, 0, 0.15);
            z-index: 1000;
        }
    </style>
</head>
<body>

    <div class="brand-banner">
        <div class="logo-circle">
            B
            <div class="logo-dot"></div>
        </div>
        <div class="brand-title">
            <h1>Bloomy<br>Technologies</h1>
        </div>
    </div>

    <div class="yearbook-container">
        <h2 class="yearbook-header-title">Linux Cloud DevOps • Class of 2026</h2>
        <p class="yearbook-subtitle">Celebrating the squad that turned “it works on my machine” into “it is live in production”</p>

        <div class="yearbook-grid">

            <!-- Instructor -->
            <div class="trainee-card instructor">
                <div class="instructor-badge">Certified Cloud Trainer</div>
                <div class="photo-box">
                    <img src="https://media.licdn.com/dms/image/v2/D4D03AQFztiH7SrTxIQ/profile-displayphoto-crop_800_800/B4DZrzfPuwIMAI-/0/1765021651518?e=1790812800&v=beta&t=zj67Guh5K9AkVbVFOwx_X1bidP13kHR1IXj57aHrXEY" alt="Akeem Oyebanji">
                </div>
                <h3 class="name">Akeem Oyebanji</h3>
                <div class="superlative">Solutions Architect</div>
                <p class="quote">"I don't just teach DevOps — I make complex concepts easy to understand so you can confidently defend your projects..."</p>
            </div>

            <!-- Abraham -->
            <div class="trainee-card">
                <div class="photo-box">
                    <img src="https://media.licdn.com/dms/image/v2/D4E35AQH2jyngV-OEjw/profile-framedphoto-shrink_800_800/B4EaCbC.wtIcAY-/0/1789307645481?e=1789934400&v=beta&t=qBgSVBFEQQm6U13ITctFiq4Xa6oKC1NKfOCucCMGSKw" alt="Abraham">
                </div>
                <h3 class="name">Abraham</h3>
                <div class="superlative">Platform Engineer Intern</div>
                <p class="quote">"people feel safe to ask questions, make mistakes, and experiment, especially for beginners."</p>
            </div>

            <!-- Oluwakemi -->
            <div class="trainee-card">
                <div class="photo-box">
                    <img src="https://api.dicebear.com/9.x/avataaars/svg?seed=Oluwakemi&backgroundColor=b6e3f4" alt="Oluwakemi">
                </div>
                <h3 class="name">Oluwakemi</h3>
                <div class="superlative">Infrastructurе Engineer Intern</div>
                <p class="quote">"The learning environment is calm, welcoming, and supportive."</p>
            </div>

            <!-- Mary -->
            <div class="trainee-card">
                <div class="photo-box">
                    <img src="https://api.dicebear.com/9.x/avataaars/svg?seed=Mary&backgroundColor=ffd5dc" alt="Mary">
                </div>
                <h3 class="name">Mary</h3>
                <div class="superlative">Cloud Architect Intern</div>
                <p class="quote">"Grow your skills with Bloomy—supportive learning that is accessible anywhere, anytime.."</p>
            </div>

            <!-- Oluwatosin -->
            <div class="trainee-card">
                <div class="photo-box">
                    <img src="https://media.licdn.com/dms/image/v2/D4E03AQE1lvJxZ64OBg/profile-displayphoto-crop_800_800/B4EZn7hK9wJAAI-/0/1760861404140?e=1790812800&v=beta&t=7VMPis6HxCR4tk7E4LJkSN6pZZ1vEjorp_S24qSUFn0" alt="Oluwatosin">
                </div>
                <h3 class="name">Oluwatosin</h3>
                <div class="superlative">Cloud Engineer Intern</div>
                <p class="quote">"Keep learning, keep growing, and keep pushing the boundaries of what's possible."</p>
            </div>

            <!-- Chukwunonso -->
            <div class="trainee-card">
                <div class="photo-box">
                    <img src="https://media.licdn.com/dms/image/v2/D4E35AQH4sOIbawBHgQ/profile-framedphoto-shrink_800_800/B4EaBTJ0SIGgAY-/0/1788101478248?e=1789898400&v=beta&t=gU8wwIAGbq5xhe1f_3wUvk-QFU-Zw6rDW6UtLneoSz8" alt="Chukwunonso">
                </div>
                <h3 class="name">Chukwunonso</h3>
                <div class="superlative">Cloud Developer Intern</div>
                <p class="quote">"letting you study from anywhere at your own convenience..."</p>
            </div>

            <!-- Samuel (fixed spelling) -->
            <div class="trainee-card">
                <div class="photo-box">
                    <img src="https://api.dicebear.com/9.x/avataaars/svg?seed=Samuel&backgroundColor=d1d4f9" alt="Samuel">
                </div>
                <h3 class="name">Samuel</h3>
                <div class="superlative">DevOps Intern</div>
                <p class="quote">"Instructors at Bloomy Technologies are highly experienced and exceptional at what they do"</p>
            </div>

            <!-- Daniel -->
            <div class="trainee-card">
                <div class="photo-box">
                    <img src="https://api.dicebear.com/9.x/avataaars/svg?seed=Daniel&backgroundColor=c0aede" alt="Daniel">
                </div>
                <h3 class="name">Daniel</h3>
                <div class="superlative">SRE Intern</div>
                <p class="quote">"Bloomy is an excellent choice for beginners..."</p>
            </div>

        </div>
    </div>

    <div class="footer-marquee-container">
        <marquee behavior="scroll" direction="left">
            Java Application Status: <strong>ONLINE</strong> • From broken code builds to live apps — we made it — Congratulations! • Powered by profAkymbo © 2026 
        </marquee>
    </div>

</body>
</html>
                """;

                byte[] response = html.getBytes("UTF-8");

        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, response.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response);
        } 
    }
}
