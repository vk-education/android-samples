# Description
MVVM is a variation of Martin Fowler's Presentation Model design pattern. It was invented by Microsoft architects Ken Cooper and Ted Peters specifically to simplify event-driven programming of user interfaces.
The pattern was incorporated into Windows Presentation Foundation (WPF) (Microsoft's .NET graphics system) and Silverlight (WPF's Internet application derivative).
John Gossman, one of Microsoft's WPF and Silverlight architects, announced MVVM on his blog in 2005.

https://docs.microsoft.com/en-us/archive/blogs/johngossman/introduction-to-modelviewviewmodel-pattern-for-building-wpf-apps

## Components
**Model** - is defined as in MVC; it is the data or business logic, completely UI independent, that stores the state and does the processing of the problem domain.

**View** - consists of the visual elements, the buttons, windows, graphics and more complex controls of a GUI

**ViewModel** - is responsible for these tasks.  The term means "Model of a View", and can be thought of as abstraction of the view, but it also provides a specialization of the Model that the View can use for data-binding.  In this latter role the ViewModel contains data-transformers that convert Model types into View types, and it contains Commands the View can use to interact with the Model.