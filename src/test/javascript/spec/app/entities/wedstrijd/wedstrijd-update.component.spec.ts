/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BluepersAppTestModule } from '../../../test.module';
import { WedstrijdUpdateComponent } from 'app/entities/wedstrijd/wedstrijd-update.component';
import { WedstrijdService } from 'app/entities/wedstrijd/wedstrijd.service';
import { Wedstrijd } from 'app/shared/model/wedstrijd.model';

describe('Component Tests', () => {
    describe('Wedstrijd Management Update Component', () => {
        let comp: WedstrijdUpdateComponent;
        let fixture: ComponentFixture<WedstrijdUpdateComponent>;
        let service: WedstrijdService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BluepersAppTestModule],
                declarations: [WedstrijdUpdateComponent]
            })
                .overrideTemplate(WedstrijdUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WedstrijdUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WedstrijdService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Wedstrijd(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.wedstrijd = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Wedstrijd();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.wedstrijd = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
