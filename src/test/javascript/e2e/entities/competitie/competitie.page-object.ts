import { element, by, promise, ElementFinder } from 'protractor';

export class CompetitieComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-competitie div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CompetitieUpdatePage {
    pageTitle = element(by.id('jhi-competitie-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    seizoenInput = element(by.id('field_seizoen'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setSeizoenInput(seizoen): promise.Promise<void> {
        return this.seizoenInput.sendKeys(seizoen);
    }

    getSeizoenInput() {
        return this.seizoenInput.getAttribute('value');
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}
