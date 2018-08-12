import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { WedstrijdComponentsPage, WedstrijdUpdatePage } from './wedstrijd.page-object';

describe('Wedstrijd e2e test', () => {
    let navBarPage: NavBarPage;
    let wedstrijdUpdatePage: WedstrijdUpdatePage;
    let wedstrijdComponentsPage: WedstrijdComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Wedstrijds', () => {
        navBarPage.goToEntity('wedstrijd');
        wedstrijdComponentsPage = new WedstrijdComponentsPage();
        expect(wedstrijdComponentsPage.getTitle()).toMatch(/bluepersApp.wedstrijd.home.title/);
    });

    it('should load create Wedstrijd page', () => {
        wedstrijdComponentsPage.clickOnCreateButton();
        wedstrijdUpdatePage = new WedstrijdUpdatePage();
        expect(wedstrijdUpdatePage.getPageTitle()).toMatch(/bluepersApp.wedstrijd.home.createOrEditLabel/);
        wedstrijdUpdatePage.cancel();
    });

    it('should create and save Wedstrijds', () => {
        wedstrijdComponentsPage.clickOnCreateButton();
        wedstrijdUpdatePage.setDatumInput('2000-12-31');
        expect(wedstrijdUpdatePage.getDatumInput()).toMatch('2000-12-31');
        wedstrijdUpdatePage.setTijdInput('2000-12-31');
        expect(wedstrijdUpdatePage.getTijdInput()).toMatch('2000-12-31');
        wedstrijdUpdatePage.setPlaatsInput('plaats');
        expect(wedstrijdUpdatePage.getPlaatsInput()).toMatch('plaats');
        wedstrijdUpdatePage.competitieSelectLastOption();
        // wedstrijdUpdatePage.teamSelectLastOption();
        wedstrijdUpdatePage.save();
        expect(wedstrijdUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
