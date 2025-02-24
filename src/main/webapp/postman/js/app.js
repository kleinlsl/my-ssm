// src/main/webapp/js/app.js
const { createApp } = Vue;

createApp({
    data() {
        return {
            method: 'GET',
            methods: ['GET', 'POST', 'PUT', 'DELETE', 'PATCH'],
            selectedDomain: 'http://up301.beta.tujia.com/',
            domains: ['http://prod/', 'http://up301.beta.tujia.com/'],
            endpoint: '',
            activeTab: 'Query Params',
            tabs: ['Query Params', 'Headers', 'Body'],
            queryParams: [{ key: '', value: '' }],
            headers: [{ key: '', value: '' }],
            body: '',
            response: null, // 初始化为 null
            loading: false,
            error: null
        };
    },
    computed: {
        url() {
            return this.selectedDomain + this.endpoint.replace(/^\//, '');
        },
        formattedResponse() {
            if (!this.response || !this.response.data) return '';
            try {
                return JSON.stringify(this.response.data, null, 2);
            } catch {
                return this.response.data;
            }
        }
    },
    methods: {
        addParam() {
            this.queryParams.push({ key: '', value: '' });
        },
        removeParam(index) {
            this.queryParams.splice(index, 1);
        },
        addHeader() {
            this.headers.push({ key: '', value: '' });
        },
        removeHeader(index) {
            this.headers.splice(index, 1);
        },
        async sendRequest() {
            this.loading = true;
            this.error = null;
            this.response = null; // 重置 response

            try {
                const browserHeaders = this.getBrowserHeaders();
                const headers = {
                    ...this.buildParams(this.headers),
                    ...browserHeaders
                };

                if (this.domains.includes(this.selectedDomain)) {
                    headers['Cookie'] = this.getCookies();
                }

                const config = {
                    method: this.method,
                    url: this.url,
                    params: this.buildParams(this.queryParams),
                    headers: headers,
                    data: this.body ? JSON.parse(this.body) : null
                };

                const res = await axios(config);
                this.response = res; // 确保 response 被正确赋值
            } catch (err) {
                this.error = err.message;
                this.response = err.response || null; // 捕获错误响应
            } finally {
                this.loading = false;
            }
        },
        buildParams(items) {
            return items
                .filter(item => item.key && item.value)
                .reduce((acc, { key, value }) => {
                    acc[key] = value;
                    return acc;
                }, {});
        },
        getBrowserHeaders() {
            return {
                'User-Agent': navigator.userAgent,
                'Accept-Language': navigator.language,
                'X-Platform': navigator.platform
            };
        },
        getCookies() {
            return document.cookie;
        }
    }
}).mount('#app');