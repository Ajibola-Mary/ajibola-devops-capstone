import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
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

        if (exchange.getRequestURI().getPath().equals("/mary-photo.jpeg")) {
            try (InputStream image = Application.class.getResourceAsStream("/mary-photo.jpeg")) {
                if (image == null) {
                    exchange.sendResponseHeaders(404, -1);
                    return;
                }

                byte[] imageBytes = image.readAllBytes();
                exchange.getResponseHeaders().set("Content-Type", "image/jpeg");
                exchange.sendResponseHeaders(200, imageBytes.length);

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(imageBytes);
                }
            }
            return;
        }

        String html = """
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Bloomy Technologies — Class of 2026</title>

    <style>
        :root {
            --ink: #101216;
            --ink-soft: #181b21;
            --paper: #f4f1ea;
            --paper-bright: #fbfaf7;
            --muted: #777b84;
            --line: rgba(16, 18, 22, 0.12);
            --blue: #3157ff;
            --blue-soft: #dfe5ff;
            --lime: #c7f36b;
            --white: #ffffff;
        }

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        html {
            scroll-behavior: smooth;
        }

        body {
            background: var(--paper);
            color: var(--ink);
            font-family: Inter, ui-sans-serif, -apple-system, BlinkMacSystemFont,
                         "Segoe UI", sans-serif;
            line-height: 1.5;
        }

        a {
            color: inherit;
            text-decoration: none;
        }

        .page {
            overflow: hidden;
        }

        /* ---------- NAV ---------- */

        .nav {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            z-index: 10;

            display: flex;
            align-items: center;
            justify-content: space-between;

            padding: 28px 6vw;

            color: var(--white);
        }

        .brand {
            display: flex;
            align-items: center;
            gap: 12px;

            font-size: 12px;
            font-weight: 800;
            letter-spacing: 0.14em;
            text-transform: uppercase;
        }

        .brand-mark {
            width: 34px;
            height: 34px;
            display: grid;
            place-items: center;

            border: 1px solid rgba(255,255,255,.35);
            border-radius: 50%;

            font-size: 11px;
        }

        .nav-meta {
            font-size: 10px;
            font-weight: 700;
            letter-spacing: .18em;
            text-transform: uppercase;
            opacity: .7;
        }

        /* ---------- HERO ---------- */

        .hero {
            position: relative;
            min-height: 92vh;

            display: flex;
            align-items: flex-end;

            padding: 150px 6vw 80px;

            color: var(--white);
            background:
                radial-gradient(circle at 78% 25%, rgba(49,87,255,.35), transparent 28%),
                radial-gradient(circle at 15% 80%, rgba(199,243,107,.08), transparent 25%),
                var(--ink);
        }

        .hero-grid {
            position: absolute;
            inset: 0;

            opacity: .13;

            background-image:
                linear-gradient(rgba(255,255,255,.25) 1px, transparent 1px),
                linear-gradient(90deg, rgba(255,255,255,.25) 1px, transparent 1px);

            background-size: 80px 80px;

            mask-image: linear-gradient(to bottom, black, transparent 80%);
        }

        .hero-content {
            position: relative;
            z-index: 2;
            max-width: 1200px;
            width: 100%;
        }

        .eyebrow {
            display: flex;
            align-items: center;
            gap: 12px;

            margin-bottom: 28px;

            color: var(--lime);

            font-size: 11px;
            font-weight: 800;
            letter-spacing: .2em;
            text-transform: uppercase;
        }

        .eyebrow::before {
            content: "";
            width: 32px;
            height: 1px;
            background: currentColor;
        }

        .hero h1 {
            max-width: 1100px;

            font-size: clamp(4rem, 10vw, 9.5rem);
            line-height: .82;
            letter-spacing: -.075em;
            font-weight: 850;
        }

        .hero h1 span {
            display: block;
            color: transparent;
            -webkit-text-stroke: 1px rgba(255,255,255,.7);
        }

        .hero-bottom {
            display: flex;
            justify-content: space-between;
            align-items: flex-end;
            gap: 40px;

            margin-top: 70px;
            padding-top: 22px;

            border-top: 1px solid rgba(255,255,255,.18);
        }

        .hero-description {
            max-width: 480px;

            color: rgba(255,255,255,.68);
            font-size: 15px;
        }

        .hero-specs {
            display: flex;
            gap: 34px;
            flex-wrap: wrap;
        }

        .spec {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }

        .spec small {
            color: rgba(255,255,255,.42);
            font-size: 9px;
            font-weight: 800;
            letter-spacing: .18em;
            text-transform: uppercase;
        }

        .spec strong {
            font-size: 12px;
            letter-spacing: .08em;
        }

        .hero-number {
            position: absolute;
            right: 4vw;
            top: 50%;

            color: rgba(255,255,255,.035);

            font-size: min(42vw, 620px);
            font-weight: 900;
            line-height: .7;
            letter-spacing: -.12em;

            transform: translateY(-50%);
            user-select: none;
        }

        /* ---------- INTRO ---------- */

        .section {
            padding: 130px 6vw;
        }

        .intro {
            display: grid;
            grid-template-columns: 1fr 1.4fr;
            gap: 10vw;

            background: var(--paper-bright);
        }

        .section-label {
            color: var(--blue);

            font-size: 10px;
            font-weight: 850;
            letter-spacing: .2em;
            text-transform: uppercase;
        }

        .intro h2 {
            margin-top: 24px;

            max-width: 480px;

            font-size: clamp(2.5rem, 5vw, 5rem);
            line-height: .95;
            letter-spacing: -.055em;
        }

        .intro-copy {
            align-self: end;

            max-width: 620px;

            color: #5e626b;
            font-size: 18px;
            line-height: 1.8;
        }

        .intro-copy strong {
            color: var(--ink);
        }

        /* ---------- JOURNEY ---------- */

        .journey {
            position: relative;
            padding: 150px 7vw 170px;
            background: var(--ink);
            color: var(--paper-bright);
            overflow: hidden;
        }

        .journey::before {
            content: "DEVOPS / 2026";
            position: absolute;
            top: 42px;
            right: 7vw;

            color: rgba(255,255,255,.28);
            font-size: 9px;
            font-weight: 800;
            letter-spacing: .22em;
            text-transform: uppercase;
        }

        .journey-header {
            display: grid;
            grid-template-columns: 1.25fr .75fr;
            gap: 8vw;
            align-items: end;
            padding-bottom: 100px;
            border-bottom: 1px solid rgba(255,255,255,.16);
        }

        .journey-kicker {
            color: var(--lime);
            font-size: 10px;
            font-weight: 850;
            letter-spacing: .2em;
            text-transform: uppercase;
        }

        .journey-header h2 {
            margin: 20px 0 0;

            font-size: clamp(4rem, 9vw, 9rem);
            line-height: .8;
            letter-spacing: -.075em;
            font-weight: 700;
        }

        .journey-header h2 span {
            color: transparent;
            -webkit-text-stroke: 1px rgba(255,255,255,.5);
        }

        .journey-header > p {
            max-width: 430px;
            margin: 0;

            color: rgba(255,255,255,.55);
            font-size: 16px;
            line-height: 1.8;
        }

        .journey-track {
            position: relative;
            padding-top: 90px;
        }

        .journey-track::before {
            content: "";
            position: absolute;
            top: 0;
            bottom: 0;
            left: 50%;

            width: 1px;
            background: linear-gradient(
                to bottom,
                transparent,
                rgba(255,255,255,.22) 8%,
                rgba(255,255,255,.22) 92%,
                transparent
            );
        }

        .journey-step {
            position: relative;

            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 8vw;

            min-height: 300px;
            padding: 50px 0;

            border-bottom: 1px solid rgba(255,255,255,.1);
        }

        .journey-step::before {
            content: "";
            position: absolute;
            left: calc(50% - 5px);
            top: 67px;

            width: 10px;
            height: 10px;

            background: var(--lime);
            border-radius: 50%;
            box-shadow: 0 0 0 7px var(--ink);
        }

        .journey-number {
            align-self: start;

            color: rgba(255,255,255,.18);
            font-size: clamp(5rem, 10vw, 10rem);
            font-weight: 800;
            line-height: .75;
            letter-spacing: -.08em;
        }

        .journey-step:nth-child(odd) .journey-number {
            text-align: right;
        }

        .journey-main {
            align-self: center;
            max-width: 560px;
        }

        .journey-step:nth-child(odd) .journey-main {
            grid-column: 2;
            grid-row: 1;
        }

        .journey-step:nth-child(even) .journey-main {
            grid-column: 1;
            grid-row: 1;
            justify-self: end;
            text-align: right;
        }

        .journey-label {
            color: var(--blue-soft);
            font-size: 9px;
            font-weight: 850;
            letter-spacing: .2em;
            text-transform: uppercase;
        }

        .journey-main h3 {
            margin: 12px 0 18px;

            font-size: clamp(3rem, 6vw, 6.5rem);
            line-height: .8;
            letter-spacing: -.07em;
            font-weight: 700;
        }

        .journey-main p {
            max-width: 470px;
            margin: 0;

            color: rgba(255,255,255,.55);
            font-size: 14px;
            line-height: 1.8;
        }

        .journey-step:nth-child(even) .journey-main p {
            margin-left: auto;
        }

        .journey-final {
            border-bottom: 0;
            padding-bottom: 20px;
        }

        .journey-final .journey-main h3 {
            color: var(--lime);
        }

        @media (max-width: 900px) {

            .journey-header {
                grid-template-columns: 1fr;
                gap: 50px;
            }

            .journey-track::before {
                left: 14px;
            }

            .journey-step {
                grid-template-columns: 70px 1fr;
                gap: 25px;
                min-height: 0;
                padding: 60px 0;
            }

            .journey-step::before {
                left: 10px;
                top: 72px;
            }

            .journey-number,
            .journey-step:nth-child(odd) .journey-number {
                text-align: left;
                font-size: 4rem;
            }

            .journey-step:nth-child(odd) .journey-main,
            .journey-step:nth-child(even) .journey-main {
                grid-column: 2;
                grid-row: 1;
                justify-self: start;
                text-align: left;
            }

            .journey-step:nth-child(even) .journey-main p {
                margin-left: 0;
            }
        }

        @media (max-width: 600px) {

            .journey {
                padding: 110px 6vw 120px;
            }

            .journey-header {
                padding-bottom: 70px;
            }

            .journey-header h2 {
                font-size: clamp(3.5rem, 17vw, 6rem);
            }

            .journey-step {
                grid-template-columns: 48px 1fr;
                gap: 18px;
            }

            .journey-number,
            .journey-step:nth-child(odd) .journey-number {
                font-size: 2.8rem;
            }

            .journey-main h3 {
                font-size: clamp(2.8rem, 14vw, 5rem);
            }
        }

        /* ---------- CLOSING ---------- */

        .closing {
            position: relative;
            min-height: 90vh;
            padding: 120px 7vw 45px;

            display: flex;
            flex-direction: column;
            justify-content: space-between;

            background: var(--paper);
            color: var(--ink);
            overflow: hidden;
        }

        .closing::before {
            content: "BLOOMY / YEARBOOK";
            position: absolute;
            top: 42px;
            left: 7vw;

            color: rgba(16,18,22,.35);
            font-size: 9px;
            font-weight: 850;
            letter-spacing: .22em;
        }

        .closing-top {
            display: flex;
            align-items: center;
            justify-content: space-between;

            padding-bottom: 24px;
            border-bottom: 1px solid var(--line);
        }

        .closing-kicker {
            color: var(--blue);
            font-size: 10px;
            font-weight: 850;
            letter-spacing: .2em;
            text-transform: uppercase;
        }

        .closing-year {
            font-size: 11px;
            font-weight: 850;
            letter-spacing: .15em;
        }

        .closing-title {
            padding: 8vh 0;
        }

        .closing-title h2 {
            margin: 0;

            font-size: clamp(4.5rem, 12vw, 13rem);
            line-height: .76;
            letter-spacing: -.085em;
            font-weight: 750;
        }

        .closing-title h2 span {
            color: transparent;
            -webkit-text-stroke: 1.5px var(--ink);
        }

        .closing-bottom {
            display: grid;
            grid-template-columns: .75fr 1.25fr;
            gap: 10vw;
            align-items: end;

            padding-top: 40px;
            border-top: 1px solid var(--line);
        }

        .closing-message {
            max-width: 430px;
            margin: 0;

            color: #62666e;
            font-size: 15px;
            line-height: 1.8;
        }

        .closing-meta {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
        }

        .closing-meta > div {
            padding-left: 18px;
            border-left: 1px solid rgba(16,18,22,.18);
        }

        .closing-meta span {
            display: block;

            margin-bottom: 10px;

            color: #5f636b;
            font-size: 9px;
            font-weight: 850;
            letter-spacing: .18em;
        }

        .closing-meta strong {
            display: block;

            font-size: 12px;
            letter-spacing: .08em;
        }

        .closing-status {
            display: flex !important;
            align-items: center;
            gap: 8px;
        }

        .closing-status i {
            display: inline-block;

            width: 7px;
            height: 7px;

            background: var(--lime);
            border-radius: 50%;
            box-shadow: none;
        }

        .closing-footer {
            display: flex;
            justify-content: space-between;

            padding-top: 28px;
            margin-top: 80px;

            border-top: 1px solid var(--line);

            color: #5f636b;
            font-size: 9px;
            font-weight: 850;
            letter-spacing: .18em;
        }

        @media (max-width: 900px) {

            .closing {
                min-height: auto;
                padding-top: 110px;
            }

            .closing-bottom {
                grid-template-columns: 1fr;
                gap: 55px;
            }

            .closing-meta {
                grid-template-columns: repeat(3, 1fr);
            }
        }

        @media (max-width: 600px) {

            .closing {
                padding: 100px 6vw 35px;
            }

            .closing-title {
                padding: 80px 0;
            }

            .closing-title h2 {
                font-size: clamp(3.8rem, 17vw, 6.5rem);
            }

            .closing-meta {
                grid-template-columns: 1fr;
                gap: 24px;
            }

            .closing-footer {
                flex-direction: column;
                gap: 12px;
            }
        }

        /* ---------- STATUS STRIP ---------- */

        .status-strip {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 20px;

            padding: 18px 6vw;

            color: var(--white);
            background: var(--blue);

            font-size: 10px;
            font-weight: 800;
            letter-spacing: .15em;
            text-transform: uppercase;
        }

        .status {
            display: flex;
            align-items: center;
            gap: 9px;
        }

        .status-dot {
            width: 7px;
            height: 7px;

            background: var(--lime);
            border-radius: 50%;

            box-shadow: 0 0 14px rgba(199,243,107,.8);
        }

        /* ---------- RESPONSIVE ---------- */

        @media (max-width: 800px) {
            .nav {
                padding: 22px 5vw;
            }

            .nav-meta {
                display: none;
            }

            .hero {
                min-height: 85vh;
                padding: 130px 5vw 55px;
            }

            .hero h1 {
                font-size: clamp(3.6rem, 17vw, 7rem);
            }

            .hero-bottom {
                align-items: flex-start;
                flex-direction: column;
                margin-top: 50px;
            }

            .hero-number {
                right: -5vw;
                top: 35%;
            }

            .section {
                padding: 90px 5vw;
            }

            .intro {
                grid-template-columns: 1fr;
                gap: 45px;
            }

            .status-strip {
                align-items: flex-start;
                flex-direction: column;
                padding: 18px 5vw;
            }
        }


        /* EDITORIAL PEOPLE */

        .people {
            background: var(--ink);
            color: var(--paper-bright);
            padding: 150px 7vw 160px;
            overflow: hidden;
        }

        .people-intro-head {
            display: grid;
            grid-template-columns: 1.3fr .7fr;
            gap: 8vw;
            align-items: end;
            margin-bottom: 110px;
        }

        .people-kicker {
            color: #858b95;
            font-size: .68rem;
            text-transform: uppercase;
            letter-spacing: .16em;
        }

        .people-title {
            margin: 18px 0 0;
            font-size: clamp(4rem, 10vw, 10rem);
            line-height: .78;
            letter-spacing: -.075em;
            font-weight: 700;
        }

        .people-title span {
            color: transparent;
            -webkit-text-stroke: 1px rgba(255,255,255,.55);
        }

        .people-description {
            max-width: 360px;
            color: #9da2ab;
            font-size: .95rem;
            line-height: 1.8;
            padding-bottom: 8px;
        }

        .people-line {
            height: 1px;
            background: rgba(255,255,255,.18);
            margin-bottom: 70px;
        }

        .people-editorial {
            display: grid;
            grid-template-columns: repeat(12, 1fr);
            column-gap: 2.2vw;
            row-gap: 110px;
        }

        .editorial-person {
            position: relative;
        }

        .editorial-person .meta {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-top: 1px solid rgba(255,255,255,.18);
            padding-top: 12px;
            margin-bottom: 22px;
            color: #777d87;
            font-size: .63rem;
            text-transform: uppercase;
            letter-spacing: .13em;
        }

        .editorial-person .role {
            color: #b9bec7;
        }

        .editorial-photo {
            position: relative;
            background: #23262d;
            overflow: hidden;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .editorial-photo img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
}

.editorial-photo::after {
            content: "";
            position: absolute;
            inset: 0;
            background: linear-gradient(
                135deg,
                rgba(49,87,255,.12),
                transparent 45%,
                rgba(199,243,107,.05)
            );
            pointer-events: none;
        }

        .editorial-placeholder {
            font-size: clamp(5rem, 9vw, 10rem);
            font-weight: 800;
            letter-spacing: -.1em;
            color: rgba(255,255,255,.08);
        }

        .editorial-person h3 {
            position: relative;
            z-index: 1;
            margin: -18px 0 0;
            padding-left: 18px;
            font-size: clamp(2rem, 4vw, 4.8rem);
            line-height: .88;
            letter-spacing: -.06em;
        }

        .editorial-person .quote {
            max-width: 360px;
            margin: 25px 0 0 18px;
            color: #969ba5;
            font-size: .88rem;
            line-height: 1.7;
        }

        .editorial-number {
            position: absolute;
            z-index: 2;
            font-size: clamp(4rem, 8vw, 8rem);
            font-weight: 800;
            line-height: .8;
            letter-spacing: -.08em;
            color: rgba(255,255,255,.07);
            pointer-events: none;
        }

        /* Mentor */

        .mentor {
            grid-column: 1 / 8;
        }

        .mentor .editorial-photo {
            aspect-ratio: 1.15 / 1;
        }

        .mentor .editorial-number {
            right: -12px;
            top: 65px;
        }

        .mentor h3 {
            font-size: clamp(3rem, 6vw, 7rem);
            margin-top: -32px;
        }

        /* Mary */

        .mary-feature {
            grid-column: 8 / 13;
            margin-top: 150px;
        }

        .mary-feature .editorial-photo {
            aspect-ratio: .82 / 1;
            background:
                linear-gradient(145deg, #dfe5ff 0%, #aebcff 100%);
        }

        .mary-feature .editorial-placeholder {
            color: rgba(16,18,22,.16);
        }

        .mary-feature .editorial-number {
            left: -22px;
            top: 30px;
            color: rgba(255,255,255,.7);
        }

        .mary-feature h3 {
            font-size: clamp(3rem, 5vw, 6rem);
        }

        /* Standard editorial profiles */

        .profile-abraham {
            grid-column: 1 / 5;
        }

        .profile-kemi {
            grid-column: 5 / 9;
            margin-top: 100px;
        }

        .profile-tosin {
            grid-column: 9 / 13;
        }

        .profile-chuk {
            grid-column: 2 / 6;
            margin-top: 80px;
        }

        .profile-samuel {
            grid-column: 6 / 10;
        }

        .profile-daniel {
            grid-column: 10 / 13;
            margin-top: 120px;
        }

        .profile-abraham .editorial-photo,
        .profile-kemi .editorial-photo,
        .profile-tosin .editorial-photo,
        .profile-chuk .editorial-photo,
        .profile-samuel .editorial-photo,
        .profile-daniel .editorial-photo {
            aspect-ratio: .82 / 1;
        }

        .profile-abraham .editorial-number,
        .profile-kemi .editorial-number,
        .profile-tosin .editorial-number,
        .profile-chuk .editorial-number,
        .profile-samuel .editorial-number,
        .profile-daniel .editorial-number {
            right: -10px;
            bottom: 25%;
        }

        @media (max-width: 900px) {
            .people-intro-head {
                grid-template-columns: 1fr;
            }

            .people-description {
                max-width: 520px;
            }

            .people-editorial {
                grid-template-columns: repeat(6, 1fr);
            }

            .mentor {
                grid-column: 1 / 5;
            }

            .mary-feature {
                grid-column: 5 / 7;
                margin-top: 80px;
            }

            .profile-abraham {
                grid-column: 1 / 3;
            }

            .profile-kemi {
                grid-column: 3 / 5;
                margin-top: 70px;
            }

            .profile-tosin {
                grid-column: 5 / 7;
            }

            .profile-chuk {
                grid-column: 1 / 3;
            }

            .profile-samuel {
                grid-column: 3 / 5;
            }

            .profile-daniel {
                grid-column: 5 / 7;
                margin-top: 80px;
            }
        }

        @media (max-width: 600px) {
            .people {
                padding: 90px 6vw 100px;
            }

            .people-intro-head {
                margin-bottom: 70px;
            }

            .people-title {
                font-size: clamp(4rem, 18vw, 7rem);
            }

            .people-editorial {
                display: block;
            }

            .editorial-person,
            .mary-feature,
            .profile-kemi,
            .profile-chuk,
            .profile-daniel {
                margin-top: 70px;
            }

            .mentor,
            .mary-feature,
            .profile-abraham,
            .profile-kemi,
            .profile-tosin,
            .profile-chuk,
            .profile-samuel,
            .profile-daniel {
                width: 100%;
            }
        }

    </style>
</head>

<body>
<div class="page">

    <nav class="nav">
        <div class="brand">
            <div class="brand-mark">B</div>
            <span>Bloomy Technologies</span>
        </div>

        <div class="nav-meta">
            Yearbook / 2026
        </div>
    </nav>

    <header class="hero">
        <div class="hero-grid"></div>

        <div class="hero-number">26</div>

        <div class="hero-content">
            <div class="eyebrow">
                Linux · AWS · DevOps
            </div>

            <h1>
                CLASS
                <span>OF ’26</span>
            </h1>

            <div class="hero-bottom">
                <p class="hero-description">
                    A record of the people, systems and late-night builds
                    behind one unforgettable technology cohort.
                </p>

                <div class="hero-specs">
                    <div class="spec">
                        <small>Cohort</small>
                        <strong>2026</strong>
                    </div>

                    <div class="spec">
                        <small>Discipline</small>
                        <strong>Cloud &amp; DevOps</strong>
                    </div>

                    <div class="spec">
                        <small>Status</small>
                        <strong>LIVE</strong>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <section class="section intro">
        <div>
            <div class="section-label">01 — The Cohort</div>

            <h2>
                We came to learn infrastructure.
            </h2>
        </div>

        <div class="intro-copy">
            <p>
                We learned that building reliable systems is about more than
                commands and configuration.
                <strong>It is about people, persistence and knowing how all
                the pieces connect.</strong>
            </p>

            <br>

            <p>
                This is a snapshot of the people who took the journey from
                Linux fundamentals to cloud infrastructure, containers,
                automation and deployment.
            </p>
        </div>
    </section>



    <section class="journey">

        <div class="journey-header">
            <div>
                <div class="journey-kicker">03 — The Journey</div>
                <h2>FROM<br><span>TERMINAL TO CLOUD.</span></h2>
            </div>

            <p>
                The tools changed. The thinking changed.
                Somewhere between the first command and the final deployment,
                the pieces started to connect.
            </p>
        </div>

        <div class="journey-track">

            <article class="journey-step">
                <span class="journey-number">01</span>
                <div class="journey-main">
                    <span class="journey-label">FOUNDATION</span>
                    <h3>LINUX</h3>
                    <p>Where the journey started — commands, processes, files, permissions and the systems underneath everything.</p>
                </div>
            </article>

            <article class="journey-step">
                <span class="journey-number">02</span>
                <div class="journey-main">
                    <span class="journey-label">CLOUD</span>
                    <h3>AWS</h3>
                    <p>Moving from local machines into infrastructure that can live, scale and operate in the cloud.</p>
                </div>
            </article>

            <article class="journey-step">
                <span class="journey-number">03</span>
                <div class="journey-main">
                    <span class="journey-label">CONTAINERS</span>
                    <h3>DOCKER</h3>
                    <p>Packaging applications and their environments so they can move from development to deployment consistently.</p>
                </div>
            </article>

            <article class="journey-step">
                <span class="journey-number">04</span>
                <div class="journey-main">
                    <span class="journey-label">INFRASTRUCTURE AS CODE</span>
                    <h3>TERRAFORM</h3>
                    <p>Turning infrastructure into code that can be planned, created and managed repeatably.</p>
                </div>
            </article>

            <article class="journey-step">
                <span class="journey-number">05</span>
                <div class="journey-main">
                    <span class="journey-label">AUTOMATION</span>
                    <h3>ANSIBLE</h3>
                    <p>Configuring servers and deploying applications without repeating every step by hand.</p>
                </div>
            </article>

            <article class="journey-step">
                <span class="journey-number">06</span>
                <div class="journey-main">
                    <span class="journey-label">CONTINUOUS DELIVERY</span>
                    <h3>CI/CD</h3>
                    <p>Connecting code, builds, infrastructure and deployment into one repeatable delivery process.</p>
                </div>
            </article>

            <article class="journey-step journey-final">
                <span class="journey-number">07</span>
                <div class="journey-main">
                    <span class="journey-label">THE BIG PICTURE</span>
                    <h3>DEVOPS</h3>
                    <p>Bringing people, processes and technology together to build, deploy and operate software reliably.</p>
                </div>
            </article>

        </div>

    </section>

    <section class="people">

        <div class="people-intro-head">
            <div>
                <div class="people-kicker">02 — The People</div>
                <h2 class="people-title">
                    THE<br><span>PEOPLE.</span>
                </h2>
            </div>

            <p class="people-description">
                Eight people. Different backgrounds. One shared journey
                through Linux, cloud infrastructure, automation and DevOps.
            </p>
        </div>

        <div class="people-line"></div>

        <div class="people-editorial">

            <!-- Akeem -->
            <article class="editorial-person mentor">
                <span class="editorial-number">01</span>

                <div class="meta">
                    <span>01 / 08</span>
                    <span class="role">Solutions Architect</span>
                </div>

                <div class="editorial-photo">
                    <img src="https://media.licdn.com/dms/image/v2/D4D03AQFztiH7SrTxIQ/profile-displayphoto-crop_800_800/B4DZrzfPuwIMAI-/0/1765021651518?e=1790812800&v=beta&t=zj67Guh5K9AkVbVFOwx_X1bidP13kHR1IXj57aHrXEY" alt="Akeem Oyebanji">
                </div>

                <h3>Akeem<br>Oyebanji</h3>

                <p class="quote">
                    “I don't just teach DevOps — I make complex concepts
                    easy to understand so you can confidently defend
                    your projects...”
                </p>
            </article>

            <!-- Mary -->
            <article class="editorial-person mary-feature">
                <span class="editorial-number">04</span>

                <div class="meta">
                    <span>04 / 08</span>
                    <span class="role">Cloud Architect Intern</span>
                </div>

                <div class="editorial-photo">
                    <img src="/mary-photo.jpeg" alt="Mary">
                </div>

                <h3>Mary</h3>

                <p class="quote">
                    “Grow your skills with Bloomy—supportive learning
                    that is accessible anywhere, anytime..”
                </p>
            </article>

            <!-- Abraham -->
            <article class="editorial-person profile-abraham">
                <span class="editorial-number">02</span>

                <div class="meta">
                    <span>02 / 08</span>
                    <span class="role">Platform Engineer Intern</span>
                </div>

                <div class="editorial-photo">
                    <span class="editorial-placeholder">A</span>
                </div>

                <h3>Abraham</h3>

                <p class="quote">
                    “people feel safe to ask questions, make mistakes,
                    and experiment, especially for beginners.”
                </p>
            </article>

            <!-- Oluwakemi -->
            <article class="editorial-person profile-kemi">
                <span class="editorial-number">03</span>

                <div class="meta">
                    <span>03 / 08</span>
                    <span class="role">Infrastructure Engineer Intern</span>
                </div>

                <div class="editorial-photo">
                    <img src="https://media.licdn.com/dms/image/v2/D4E03AQE1lvJxZ64OBg/profile-displayphoto-crop_800_800/B4EZn7hK9wJAAI-/0/1760861404140?e=1790812800&v=beta&t=7VMPis6HxCR4tk7E4LJkSN6pZZ1vEjorp_S24qSUFn0" alt="Oluwatosin">
                </div>

                <h3>Oluwakemi</h3>

                <p class="quote">
                    “The learning environment is calm, welcoming,
                    and supportive.”
                </p>
            </article>

            <!-- Oluwatosin -->
            <article class="editorial-person profile-tosin">
                <span class="editorial-number">05</span>

                <div class="meta">
                    <span>05 / 08</span>
                    <span class="role">Cloud Engineer Intern</span>
                </div>

                <div class="editorial-photo">
                    <img src="https://api.dicebear.com/9.x/avataaars/svg?seed=Oluwakemi&backgroundColor=b6e3f4" alt="Oluwakemi">
                </div>

                <h3>Oluwatosin</h3>

                <p class="quote">
                    “Keep learning, keep growing, and keep pushing
                    the boundaries of what's possible.”
                </p>
            </article>

            <!-- Chukwunonso -->
            <article class="editorial-person profile-chuk">
                <span class="editorial-number">06</span>

                <div class="meta">
                    <span>06 / 08</span>
                    <span class="role">Cloud Developer Intern</span>
                </div>

                <div class="editorial-photo">
                    <span class="editorial-placeholder">C</span>
                </div>

                <h3>Chukwunonso</h3>

                <p class="quote">
                    “letting you study from anywhere at your own
                    convenience...”
                </p>
            </article>

            <!-- Samuel -->
            <article class="editorial-person profile-samuel">
                <span class="editorial-number">07</span>

                <div class="meta">
                    <span>07 / 08</span>
                    <span class="role">DevOps Intern</span>
                </div>

                <div class="editorial-photo">
                    <img src="https://api.dicebear.com/9.x/avataaars/svg?seed=Samuel&backgroundColor=d1d4f9" alt="Samuel">
                </div>

                <h3>Samuel</h3>

                <p class="quote">
                    “Instructors at Bloomy Technologies are highly
                    experienced and exceptional at what they do”
                </p>
            </article>

            <!-- Daniel -->
            <article class="editorial-person profile-daniel">
                <span class="editorial-number">08</span>

                <div class="meta">
                    <span>08 / 08</span>
                    <span class="role">SRE Intern</span>
                </div>

                <div class="editorial-photo">
                    <img src="https://api.dicebear.com/9.x/avataaars/svg?seed=Daniel&backgroundColor=c0aede" alt="Daniel">
                </div>

                <h3>Daniel</h3>

                <p class="quote">
                    “Bloomy is an excellent choice for beginners...”
                </p>
            </article>

        </div>
    </section>

    <section class="closing">

        <div class="closing-top">
            <span class="closing-kicker">04 — The End / The Beginning</span>
            <span class="closing-year">2026</span>
        </div>

        <div class="closing-title">
            <h2>
                WE BUILT<br>
                <span>MORE THAN</span><br>
                SYSTEMS.
            </h2>
        </div>

        <div class="closing-bottom">

            <p class="closing-message">
                From the first Linux command to a deployed application,
                this cohort learned that technology is never just about
                the tools. It is about what we build with them.
            </p>

            <div class="closing-meta">
                <div>
                    <span>COHORT</span>
                    <strong>CLASS OF ’26</strong>
                </div>

                <div>
                    <span>DISCIPLINE</span>
                    <strong>CLOUD &amp; DEVOPS</strong>
                </div>

                <div>
                    <span>APPLICATION</span>
                    <strong class="closing-status">
                        <i></i> ONLINE
                    </strong>
                </div>
            </div>

        </div>

        <div class="closing-footer">
            <span>BLOOMY TECHNOLOGIES</span>
            <span>JAVA YEARBOOK / 2026</span>
        </div>

    </section>

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
