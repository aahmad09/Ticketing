const ce = React.createElement

class LoginField extends React.Component {
    constructor(props) {
        super(props)
        this.submit = props.submit
        this.state = {email: "", password: ""}
    }

    handleEmailChange(e) {
        const val = e.target.value;
        this.setState((s) => {return {...s, email: val}});
    }
    handlePasswordChange(e) {
        const val = e.target.value;
        this.setState((s) => {return {...s, password: val}});
    }
    handleSubmit(e) {
        this.submit({...this.state});
    }

    render() {
        return ce('div', {className: 'login_area'}, 
            ce('div', {className: 'labeled_field'}, 
                ce('Email: ',{type:'label'}),
                ce('input', {type: 'text', value: this.state.email, onChange: (e) => this.handleEmailChange(e)}),
            ),
            ce('div', {className: 'labeled_field'}, 
            ce('Password: ',{type:'label'}),
                ce('input', {type: 'password', value: this.state.password, onChange: (e) => this.handlePasswordChange(e)}),
            ),
            ce('button', {onClick: (e) => this.handleSubmit(e)}, "Submit"),
        )
    }
}

export default LoginField