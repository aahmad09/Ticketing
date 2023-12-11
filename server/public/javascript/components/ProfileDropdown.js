const ce = React.createElement

const profilePicRoute = document.getElementById("profilePicRoute").value;
const DashboardRoute = document.getElementById("DashboardRoute").value;
const LogoutRoute = document.getElementById("LogoutRoute").value;
const CreateEventPageRoute = document.getElementById("CreateEventPageRoute").value;

class ProfileDropdown extends React.Component {
    constructor(props) {
        super(props)
        this.img = props.img
        this.state = {open: false, isOrganizer: props.isOrganizer}
        
    }

    toggleDropdown = (e) => {
        const val = e.target.value;
        this.setState((s) => {return {...s, open: !this.state.open}});
    }

    render() {
        if (this.state.open) {
            return ce('div', {className: 'dropdown', onClick: this.toggleDropdown}, 
                ce(DropdownLink, {name: 'Home', route: DashboardRoute}, null),
                ((this.state.isOrganizer) && ce(DropdownLink, {name: 'Create Event', route: CreateEventPageRoute}, null)),
                ce(DropdownLink, {name: 'Logout', route: LogoutRoute}, null)
            )
        } else {
            return ce('div', {onClick: this.toggleDropdown}, 
                ce('img',{className: 'profile-pic', type: "image/png", src: profilePicRoute}),
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
        return ce('div', {className: 'dropdown-link', onClick: () => {
            fetch(this.route);
            window.location.href = this.route;
        }}, this.name)
    }

}

export default ProfileDropdown