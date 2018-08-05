import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { CompetitieComponentsPage, CompetitieUpdatePage } from './competitie.page-object';

describe('Competitie e2e test', () => {
    let navBarPage: NavBarPage;
    let competitieUpdatePage: CompetitieUpdatePage;
    let competitieComponentsPage: CompetitieComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Competities', () => {
        navBarPage.goToEntity('competitie');
        competitieComponentsPage = new CompetitieComponentsPage();
        expect(competitieComponentsPage.getTitle()).toMatch(/bluepersApp.competitie.home.title/);
    });

    it('should load create Competitie page', () => {
        competitieComponentsPage.clickOnCreateButton();
        competitieUpdatePage = new CompetitieUpdatePage();
        expect(competitieUpdatePage.getPageTitle()).toMatch(/bluepersApp.competitie.home.createOrEditLabel/);
        competitieUpdatePage.cancel();
    });

    it('should create and save Competities', () => {
        competitieComponentsPage.clickOnCreateButton();
        competitieUpdatePage.setSeizoenInput('seizoen');
        expect(competitieUpdatePage.getSeizoenInput()).toMatch('seizoen');
        competitieUpdatePage.save();
        expect(competitieUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
