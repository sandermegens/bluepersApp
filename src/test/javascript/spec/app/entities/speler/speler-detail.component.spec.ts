/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BluepersAppTestModule } from '../../../test.module';
import { SpelerDetailComponent } from 'app/entities/speler/speler-detail.component';
import { Speler } from 'app/shared/model/speler.model';

describe('Component Tests', () => {
    describe('Speler Management Detail Component', () => {
        let comp: SpelerDetailComponent;
        let fixture: ComponentFixture<SpelerDetailComponent>;
        const route = ({ data: of({ speler: new Speler(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BluepersAppTestModule],
                declarations: [SpelerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SpelerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SpelerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.speler).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
