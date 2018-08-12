import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { TeamComponentsPage, TeamUpdatePage } from './team.page-object';

describe('Team e2e test', () => {
    let navBarPage: NavBarPage;
    let teamUpdatePage: TeamUpdatePage;
    let teamComponentsPage: TeamComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Teams', () => {
        navBarPage.goToEntity('team');
        teamComponentsPage = new TeamComponentsPage();
        expect(teamComponentsPage.getTitle()).toMatch(/bluepersApp.team.home.title/);
    });

    it('should load create Team page', () => {
        teamComponentsPage.clickOnCreateButton();
        teamUpdatePage = new TeamUpdatePage();
        expect(teamUpdatePage.getPageTitle()).toMatch(/bluepersApp.team.home.createOrEditLabel/);
        teamUpdatePage.cancel();
    });

    it('should create and save Teams', () => {
        teamComponentsPage.clickOnCreateButton();
        teamUpdatePage.setNaamInput('naam');
        expect(teamUpdatePage.getNaamInput()).toMatch('naam');
        // teamUpdatePage.competitieSelectLastOption();
        teamUpdatePage.save();
        expect(teamUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
