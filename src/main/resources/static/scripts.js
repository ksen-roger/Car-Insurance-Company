// Improved scripts.js — safe checks, proper endpoints and error handling
document.addEventListener('DOMContentLoaded', () => {
    // const config = {
    // const config = { baseUrl: '/api', endpoints: { register: '/insurance', policy: '/insurance', claim: '/claims' } };

    const config = {
        baseUrl: 'http://localhost:8080/api',
        endpoints: {
            register: '/insurance',
            policy: '/insurance',
            claim: '/claims'
        }
    };

    //     baseUrl: 'http://localhost:8080/api', // backend base
    //     endpoints: {
    //         // Align these to your backend. Your CarInsuranceController uses /api/insurance
    //         register: '/insurance', // if you later implement a dedicated customers endpoint, change this
    //         policy: '/insurance',   // POST -> /api/insurance (uses your CarInsuranceController)
    //         claim: '/claims'        // you need to implement /api/claims on backend or change this
    //     }
    // };

    // helpers: UI
    const toast = document.getElementById('toast');
    function showToast(msg, type='info', timeout=3000) {
        if (!toast) {
            alert(msg);
            return;
        }
        toast.textContent = msg;
        toast.className = 'toast';
        if (type === 'success') toast.classList.add('success');
        if (type === 'error') toast.classList.add('error');
        toast.classList.remove('hidden');
        setTimeout(() => toast.classList.add('hidden'), timeout);
    }

    // Login (unchanged)
    const loginForm = document.getElementById('loginForm');
    const loginCard = document.getElementById('loginCard');
    const appShell = document.getElementById('appShell');
    if (loginForm) {
        loginForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const username = loginForm.username.value.trim();
            const password = loginForm.password.value;
            if (username === 'admin' && password === '1234') {
                loginCard.classList.add('hidden');
                appShell.classList.remove('hidden');
                showSection('register');
                showToast('Welcome back, admin!', 'success', 2200);
            } else {
                showToast('Login failed — use admin / 1234', 'error', 2600);
            }
        });
    }

    // Navigation
    const navButtons = document.querySelectorAll('.nav-btn');
    navButtons.forEach(btn => btn.addEventListener('click', () => {
        document.querySelectorAll('.nav-btn').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
        showSection(btn.dataset.section);
    }));

    window.showSection = function(sectionId) {
        document.querySelectorAll('.section').forEach(s => s.classList.add('hidden'));
        const el = document.getElementById(sectionId);
        if (el) el.classList.remove('hidden');
    };

    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) logoutBtn.addEventListener('click', () => {
        if (appShell) appShell.classList.add('hidden');
        if (loginCard) loginCard.classList.remove('hidden');
        showToast('Logged out', 'info', 1500);
    });

    // generic POST helper with better error handling
    async function postJson(url, data) {
        try {
            const res = await fetch(url, {
                method: 'POST',
                headers: {'Content-Type':'application/json'},
                body: JSON.stringify(data)
            });
            const text = await res.text();
            // try to parse JSON if any
            let json;
            try { json = text ? JSON.parse(text) : null; } catch(_) { json = text; }
            if (!res.ok) {
                const message = (json && json.message) ? json.message : (typeof json === 'string' ? json : res.statusText);
                throw new Error(message || `HTTP ${res.status}`);
            }
            return json;
        } catch (err) {
            throw err;
        }
    }

    // Register Form (keeps current behaviour but points to /api/insurance for now)
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const fd = new FormData(registerForm);
            const payload = Object.fromEntries(fd.entries());

            try {
                const url = config.baseUrl + config.endpoints.register;
                await postJson(url, payload);
                showToast('Customer & car registered', 'success');
                registerForm.reset();
            } catch (err) {
                showToast('Registration failed: ' + (err.message || err), 'error');
                console.error(err);
            }
        });
    }

    // Policy Form -> POST to /api/insurance (matches CarInsuranceController)
    const policyForm = document.getElementById('policyForm');
    if (policyForm) {
        policyForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const fd = new FormData(policyForm);
            const payload = Object.fromEntries(fd.entries());

            // optional: rename fields to backend expectations
            // e.g., if your backend expects startDate and endDate as ISO strings, they already are from date inputs
            try {
                const url = config.baseUrl + config.endpoints.policy; // -> /api/insurance
                const res = await postJson(url, payload);
                showToast('Policy submitted successfully', 'success');
                policyForm.reset();
                console.log('Policy response:', res);
            } catch (err) {
                showToast('Policy submit failed: ' + (err.message || err), 'error');
                console.error(err);
            }
        });
    }

    // Claim Form (if you don't have /api/claims implemented, this will 404)
    const claimForm = document.getElementById('claimForm');
    if (claimForm) {
        claimForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const fd = new FormData(claimForm);
            const payload = Object.fromEntries(fd.entries());
            try {
                const url = config.baseUrl + config.endpoints.claim;
                await postJson(url, payload);
                showToast('Claim filed successfully', 'success');
                claimForm.reset();
            } catch (err) {
                showToast('Claim failed: ' + (err.message || err), 'error');
                console.error(err);
            }
        });
    }

    // Search mock
    const globalSearch = document.getElementById('globalSearch');
    if (globalSearch) {
        globalSearch.addEventListener('input', (e) => {
            const q = e.target.value.trim().toLowerCase();
            if (!q) return;
            showToast('Search: ' + q, 'info', 900);
        });
    }
});