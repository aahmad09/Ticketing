import LoginField from '../components/LoginField.js'


const ce = React.createElement

const registerRoute = document.getElementById("RegisterRoute").value;
const DashboardRoute = document.getElementById("DashboardRoute").value;


class LoginPage extends React.Component {
    constructor(props) {
        super(props)
        this.relocate = props.relocate;
        this.login = (params) => {
            fetch("handleLogin", {
                method: "POST",
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(params)
            }).then(res => res.json()).then(data => {
                if (data) {
                    fetch(DashboardRoute);
                }
            })
        }
    }

    handleRoute(e) {
        fetch(registerRoute);
    }

    render() {
        
        return ce('div',{className: "login-area"},
            ce('h2', null, "Login: "),
            ce(LoginField, { submit: this.login }, null),
            ce('hr', null, null),
            ce('h3', {onClick: e => this.handleRoute(e)}, "Create an Account ->", ce('i', {className: 'fa-long-arrow-right'}))
        )
    }
}


ReactDOM.render(
    React.createElement(LoginPage, {}, null),
    document.getElementById('react-root')
);