import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { SpelerComponentsPage, SpelerUpdatePage } from './speler.page-object';

describe('Speler e2e test', () => {
    let navBarPage: NavBarPage;
    let spelerUpdatePage: SpelerUpdatePage;
    let spelerComponentsPage: SpelerComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Spelers', () => {
        navBarPage.goToEntity('speler');
        spelerComponentsPage = new SpelerComponentsPage();
        expect(spelerComponentsPage.getTitle()).toMatch(/bluepersApp.speler.home.title/);
    });

    it('should load create Speler page', () => {
        spelerComponentsPage.clickOnCreateButton();
        spelerUpdatePage = new SpelerUpdatePage();
        expect(spelerUpdatePage.getPageTitle()).toMatch(/bluepersApp.speler.home.createOrEditLabel/);
        spelerUpdatePage.cancel();
    });

    it('should create and save Spelers', () => {
        spelerComponentsPage.clickOnCreateButton();
        spelerUpdatePage.setVoornaamInput('voornaam');
        expect(spelerUpdatePage.getVoornaamInput()).toMatch('voornaam');
        spelerUpdatePage.setTussenvoegselInput('tussenvoegsel');
        expect(spelerUpdatePage.getTussenvoegselInput()).toMatch('tussenvoegsel');
        spelerUpdatePage.setAchternaamInput('achternaam');
        expect(spelerUpdatePage.getAchternaamInput()).toMatch('achternaam');
        spelerUpdatePage.setRugnummerInput('5');
        expect(spelerUpdatePage.getRugnummerInput()).toMatch('5');
        spelerUpdatePage.positieSelectLastOption();
        spelerUpdatePage.setGeboorteDatumInput('2000-12-31');
        expect(spelerUpdatePage.getGeboorteDatumInput()).toMatch('2000-12-31');
        spelerUpdatePage.setDebuutInput('2000-12-31');
        expect(spelerUpdatePage.getDebuutInput()).toMatch('2000-12-31');
        spelerUpdatePage.setBijzonderhedenInput('bijzonderheden');
        expect(spelerUpdatePage.getBijzonderhedenInput()).toMatch('bijzonderheden');
        spelerUpdatePage.teamSelectLastOption();
        spelerUpdatePage.save();
        expect(spelerUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
