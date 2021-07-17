import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarComponent } from './navbar.component';

describe('NavbarComponent', () => {
	let component: NavbarComponent;
	let fixture: ComponentFixture<NavbarComponent>;

	beforeEach(() => {
		fixture = TestBed.createComponent(NavbarComponent);
		component = fixture.componentInstance;
		component.ngOnInit();
	});

	afterEach(() => {
		fixture = undefined;
		component = undefined;
	});

	it('should be created', () => {
		expect(component).toBeTruthy();
	});
});
