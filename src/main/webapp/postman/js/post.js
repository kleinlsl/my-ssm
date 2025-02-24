// src/main/webapp/js/app.js
const { createApp } = Vue;

createApp({
    data() {
        return {
            method: 'GET',
            methods: ['GET', 'POST', 'PUT', 'DELETE', 'PATCH'],
            selectedDomain: 'http://prod/',
            domains: ['http://prod/', 'http://beta/'],
            endpoint: '',
            activeTab: 'Query Params',
            tabs: ['Query Params', 'Headers', 'Body'],
            queryParams: [{ key: '', value: '' }],
            headers: [{ key: '', value: '' }],
            body: '',
            response: null,
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
            this.response = null;

            try {
                // 构造请求参数
                const requestData = {
                    url: this.url,
                    method: this.method,
                    params: this.buildParams(this.queryParams),
                    headers: this.buildParams(this.headers),
                    data: this.body ? JSON.parse(this.body) : null
                };

                // 发送请求到后端代理接口
                const res = await axios.post('/api/proxy', requestData);
                this.response = res.data; // 后端返回的响应
            } catch (err) {
                this.error = err.message;
                this.response = err.response?.data || null;
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
        }
    }
}).mount('#app');