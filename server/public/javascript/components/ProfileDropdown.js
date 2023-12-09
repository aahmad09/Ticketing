const ce = React.createElement

const profilePicRoute = document.getElementById("profilePicRoute").value;

class ProfileDropdown extends React.Component {
    constructor(props) {
        super(props)
        this.img = props.img
        this.state = {open: false}
    }

    toggleDropdown = (e) => {
        const val = e.target.value;
        this.setState((s) => {return {...s, open: !this.state.open}});
    }

    render() {
        if (this.state.open) {
            return ce('div', {className: 'dropdown', onClick: this.toggleDropdown}, 
                ce(DropdownLink, {name: 'Home', route: 'dashboard'}, null),
                ce(DropdownLink, {name: 'Create Event', route: 'dashboard'}, null),
                ce(DropdownLink, {name: 'Logout', route: 'login'}, null)
            )
        } else {
            return ce('div', {onClick: this.toggleDropdown}, 
                //ce('img',{src: profilePicRoute},""),
                null
            )
        }
        
    }
}

class DropdownLink extends React.Component {
    constructor(props) {
        super(props)
        this.route = props.route;
        this.name = props.name;
    }

    render() {
        return ce('div', {className: 'dropdown-link', onClick: () => fetch(this.route)}, this.name)
    }

}

export default ProfileDropdown